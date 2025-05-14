package dev.nicacio.exchbook.controller;

import dev.nicacio.exchbook.dtos.request.CreateBookRequestDto;
import dev.nicacio.exchbook.dtos.request.UpdateBookRequestDto;
import dev.nicacio.exchbook.dtos.response.BookDto;
import dev.nicacio.exchbook.exceptions.ResourceNotFoundException;
import dev.nicacio.exchbook.services.BookService;
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
public class BookController {

    private final BookService bookService;
    @PostMapping("/book")
    public ResponseEntity addBook(@RequestBody @Valid CreateBookRequestDto create){
        int response = bookService.registerBook(create);
        URI location = UriComponentsBuilder
                .fromPath("/v1/book/{id}")
                .buildAndExpand()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") int idBook) throws ResourceNotFoundException {
        BookDto response = bookService.findBookById(idBook);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAllBooks(){
        List<BookDto> response = bookService.findAllBooks();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity updateBook(@PathVariable("id") int idBook, @RequestBody UpdateBookRequestDto updateBook){
        bookService.updateBook(idBook,updateBook);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/book/{id}")
    public ResponseEntity deleteBook(@PathVariable("id") int idBook){
        bookService.deleteBook(idBook);
        return ResponseEntity.noContent().build();
    }
}
