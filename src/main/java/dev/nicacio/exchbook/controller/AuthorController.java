package dev.nicacio.exchbook.controller;

import dev.nicacio.exchbook.dtos.request.CreateAuthorRequestDto;
import dev.nicacio.exchbook.dtos.response.AuthorDto;
import dev.nicacio.exchbook.models.Author;
import dev.nicacio.exchbook.services.AuthorService;
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
public class AuthorController {

    private final AuthorService authorService;
    @PostMapping("/author")
    public ResponseEntity addAuthor(@RequestBody CreateAuthorRequestDto create){
        HttpHeaders headers = new HttpHeaders();
        int idAuthor = authorService.registerAuthor(create);
        headers.setLocation(UriComponentsBuilder
                .newInstance()
                .path("/v1/author/{id}")
                .buildAndExpand(idAuthor)
                .toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<AuthorDto> returnAuthorById(@PathVariable("id") int idAuthor) throws ChangeSetPersister.NotFoundException {
        AuthorDto response = authorService.getAuthorById(idAuthor);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDto>> returnAllAuthors(){
        List<AuthorDto> response = authorService.getAllAuthors();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
