package dev.nicacio.exchbook.controller;

import dev.nicacio.exchbook.dtos.request.CreateBookEditionRequestDto;
import dev.nicacio.exchbook.dtos.request.UpdateEditionRequestDto;
import dev.nicacio.exchbook.dtos.response.BookEditionDto;
import dev.nicacio.exchbook.exceptions.ResourceNotFoundException;
import dev.nicacio.exchbook.services.BookEditionService;
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
public class BookEditionController {
    private final BookEditionService bookEditionService;
    @PostMapping("/edition")
    public ResponseEntity addBookEdition(@RequestBody @Valid CreateBookEditionRequestDto createEdition){
        HttpHeaders headers = new HttpHeaders();
        int response = bookEditionService.registerEdition(createEdition);

        headers.setLocation(UriComponentsBuilder
                .newInstance()
                .path("/v1/edition/{id}")
                .buildAndExpand(response)
                .toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    @GetMapping("/edition/{id}")
    public  ResponseEntity<BookEditionDto> getBookEditionById(@PathVariable("id") int idBookEdition) throws ResourceNotFoundException {
        BookEditionDto response = bookEditionService.findBookEditionById(idBookEdition);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/editions")
    public ResponseEntity<List<BookEditionDto>> getAllBookEditions(){
        List<BookEditionDto> response = bookEditionService.findAllBookEdition();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PutMapping("/edition/{id}")
    public ResponseEntity updateEdition(@PathVariable("id") int idEdition, @RequestBody UpdateEditionRequestDto update){
        bookEditionService.updateEdition(idEdition,update);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
