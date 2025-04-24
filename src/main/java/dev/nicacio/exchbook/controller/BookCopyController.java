package dev.nicacio.exchbook.controller;

import dev.nicacio.exchbook.dtos.request.CreateBookCopyRequestDto;
import dev.nicacio.exchbook.dtos.response.BookCopyDto;
import dev.nicacio.exchbook.services.BookCopyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class BookCopyController {
    private final BookCopyService bookCopyServices;
    @PostMapping("/copy")
    public ResponseEntity addBookCopy(@RequestBody CreateBookCopyRequestDto request){
        HttpHeaders headers = new HttpHeaders();
        int response = bookCopyServices.registerBookCopy(request);

        headers.setLocation(UriComponentsBuilder
                .newInstance()
                .path("/v1/copy/{id}")
                .buildAndExpand(response)
                .toUri());
        return new ResponseEntity<>(headers,HttpStatus.CREATED);
    }

    @GetMapping("/copy/{id}")
    public ResponseEntity<BookCopyDto> returnBookCopyById(@PathVariable("id") int idBookCopy) throws ChangeSetPersister.NotFoundException {
        BookCopyDto response = bookCopyServices.getBookCopyById(idBookCopy);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/copies")
    public ResponseEntity<List<BookCopyDto>> getAllBookCopies(){
        List<BookCopyDto> response = bookCopyServices.getAllBookCopies();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
