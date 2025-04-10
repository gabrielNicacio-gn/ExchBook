package dev.nicacio.exchbook.controller;

import dev.nicacio.exchbook.dtos.request.CreateBookCopyRequestDto;
import dev.nicacio.exchbook.dtos.response.BookCopyDto;
import dev.nicacio.exchbook.services.BookCopyService;
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
}
