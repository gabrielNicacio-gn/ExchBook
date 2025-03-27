package dev.nicacio.exchbook.controller;

import dev.nicacio.exchbook.dtos.request.CreateBookCopyRequestDto;
import dev.nicacio.exchbook.dtos.response.CreateBookCopyResponseDto;
import dev.nicacio.exchbook.services.BookCopyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class BookCopyController {
    private final BookCopyService bookCopyServices;
    @PostMapping("/copy")
    public ResponseEntity<CreateBookCopyResponseDto> addBookCopy(CreateBookCopyRequestDto request){
        HttpHeaders headers = new HttpHeaders();
        CreateBookCopyResponseDto response = bookCopyServices.registerBookCopy(request);
        if(!response.getErrors().isEmpty()){
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        headers.setLocation(UriComponentsBuilder
                .newInstance()
                .path("v1/copy/{id}")
                .buildAndExpand(response.getIdCopy())
                .toUri());
        return new ResponseEntity<>(response,headers,HttpStatus.CREATED);
    }
}
