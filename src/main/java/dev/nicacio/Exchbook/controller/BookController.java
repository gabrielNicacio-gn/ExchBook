package dev.nicacio.Exchbook.controller;

import dev.nicacio.Exchbook.dtos.request.CreateBookRequestDto;
import dev.nicacio.Exchbook.dtos.response.CreateBookResponseDto;
import dev.nicacio.Exchbook.services.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class BookController {

    private BookServices bookServices;
    @Autowired
    public BookController(BookServices bookServices){
        this.bookServices = bookServices;
    }

    @PostMapping("/copy")
    public ResponseEntity<CreateBookResponseDto> AddBook(CreateBookRequestDto request){
        HttpHeaders headers = new HttpHeaders();
        var response = bookServices.RegisterBook(request);
        headers.setLocation(UriComponentsBuilder
                .newInstance()
                .path("api/copy/{id}")
                .buildAndExpand(response.IdCopy())
                .toUri());
        return new ResponseEntity<>(response,headers,HttpStatus.CREATED);
    }
}
