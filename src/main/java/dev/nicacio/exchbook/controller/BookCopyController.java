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

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class BookCopyController {
    private final BookCopyService bookCopyServices;
    @PostMapping("/copy")
    public ResponseEntity addBookCopy(@RequestBody @Valid CreateBookCopyRequestDto request){
        int response = bookCopyServices.registerBookCopy(request);
        URI location = UriComponentsBuilder
                .fromPath("/v1/copy/{id}")
                .buildAndExpand(response)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/copy/{id}")
    public ResponseEntity<BookCopyDto> getBookCopyById(@PathVariable("id") int idBookCopy) throws ResourceNotFoundException {
        BookCopyDto response = bookCopyServices.findBookCopyById(idBookCopy);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/copies")
    public ResponseEntity<List<BookCopyDto>> getAllBookCopies(){
        List<BookCopyDto> response = bookCopyServices.findAllBookCopies();
        return ResponseEntity.ok(response);
    }
    @PutMapping("/copy/{id}")
    public ResponseEntity updateBookCopy(@PathVariable("id") int idCopy, @RequestBody UpdateBookCopyRequestDto updateBookCopyRequestDto){
        bookCopyServices.updateBookCopy(idCopy,updateBookCopyRequestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/copy/{id}")
    public ResponseEntity deleteBookCopy(@PathVariable("id") int idCopy){
        bookCopyServices.deleteBookCopy(idCopy);
        return ResponseEntity.noContent().build();
    }
}
