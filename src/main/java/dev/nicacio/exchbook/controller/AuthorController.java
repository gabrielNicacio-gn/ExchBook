package dev.nicacio.exchbook.controller;

import dev.nicacio.exchbook.dtos.request.CreateAuthorRequestDto;
import dev.nicacio.exchbook.dtos.request.CreateBookRequestDto;
import dev.nicacio.exchbook.dtos.response.CreateAuthorResponseDto;
import dev.nicacio.exchbook.dtos.response.CreateBookResponseDto;
import dev.nicacio.exchbook.services.AuthorService;
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
@RequestMapping("v1/")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    @PostMapping("/book")
    public ResponseEntity<CreateAuthorResponseDto> addAuthor(@RequestBody CreateAuthorRequestDto create){
        HttpHeaders headers = new HttpHeaders();
        var response = authorService.registerAuthor(create);
        headers.setLocation(UriComponentsBuilder
                .newInstance()
                .path("v1/copy/{id}")
                .buildAndExpand(response.IdAuthor())
                .toUri());
        return new ResponseEntity<>(response,headers, HttpStatus.CREATED);
    }
}
