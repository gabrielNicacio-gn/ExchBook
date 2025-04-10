package dev.nicacio.exchbook.controller;

import dev.nicacio.exchbook.dtos.request.CreateBookRequestDto;
import dev.nicacio.exchbook.dtos.response.BookDto;
import dev.nicacio.exchbook.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    @PostMapping("/book")
    public ResponseEntity addBook(@RequestBody CreateBookRequestDto create){
        HttpHeaders headers = new HttpHeaders();
        int response = bookService.registerBook(create);
        headers.setLocation(UriComponentsBuilder
                .newInstance()
                .path("/v1/book/{id}")
                .buildAndExpand(response)
                .toUri());
        return new ResponseEntity<>(headers,HttpStatus.CREATED);
    }
}
