package dev.nicacio.exchbook.controller;

import dev.nicacio.exchbook.dtos.request.CreateBookEditionRequestDto;
import dev.nicacio.exchbook.dtos.response.BookEditionDto;
import dev.nicacio.exchbook.services.BookEditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class BookEditionController {
    private final BookEditionService bookEditionService;
    @PostMapping("/edition")
    public ResponseEntity addBookEdition(@RequestBody CreateBookEditionRequestDto createEdition){
        HttpHeaders headers = new HttpHeaders();
        int response = bookEditionService.registerEdition(createEdition);

        headers.setLocation(UriComponentsBuilder
                .newInstance()
                .path("/v1/edition/{id}")
                .buildAndExpand(response)
                .toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
