package dev.nicacio.exchbook.controller;

import dev.nicacio.exchbook.dtos.request.CreateBookCopyRequestDto;
import dev.nicacio.exchbook.dtos.request.UpdateBookCopyRequestDto;
import dev.nicacio.exchbook.dtos.response.BookCopyDto;
import dev.nicacio.exchbook.exceptions.ResourceNotFoundException;
import dev.nicacio.exchbook.services.BookCopyService;
import jakarta.validation.Valid;
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
    public ResponseEntity addBookCopy(@RequestBody @Valid CreateBookCopyRequestDto request){
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
    public ResponseEntity<BookCopyDto> getBookCopyById(@PathVariable("id") int idBookCopy) throws ResourceNotFoundException {
        BookCopyDto response = bookCopyServices.findBookCopyById(idBookCopy);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/copies")
    public ResponseEntity<List<BookCopyDto>> getAllBookCopies(){
        List<BookCopyDto> response = bookCopyServices.findAllBookCopies();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PutMapping("/copy/{id}")
    public ResponseEntity updateBookCopy(@PathVariable("id") int idCopy, @RequestBody UpdateBookCopyRequestDto updateBookCopyRequestDto){
        bookCopyServices.updateBookCopy(idCopy,updateBookCopyRequestDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/copy/{id}")
    public ResponseEntity deleteBookCopy(@PathVariable("id") int idCopy){
        bookCopyServices.deleteBookCopy(idCopy);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
