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

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class BookEditionController {
    private final BookEditionService bookEditionService;
    @PostMapping("/edition")
    public ResponseEntity addBookEdition(@RequestBody @Valid CreateBookEditionRequestDto createEdition){
        int response = bookEditionService.registerEdition(createEdition);

        URI location = UriComponentsBuilder
                .fromPath("/v1/edition/{id}")
                .buildAndExpand(response)
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @GetMapping("/edition/{id}")
    public  ResponseEntity<BookEditionDto> getBookEditionById(@PathVariable("id") int idBookEdition) throws ResourceNotFoundException {
        BookEditionDto response = bookEditionService.findBookEditionById(idBookEdition);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/editions")
    public ResponseEntity<List<BookEditionDto>> getAllBookEditions(){
        List<BookEditionDto> response = bookEditionService.findAllBookEdition();
        return ResponseEntity.ok(response);
    }
    @PutMapping("/edition/{id}")
    public ResponseEntity updateEdition(@PathVariable("id") int idEdition, @RequestBody @Valid UpdateEditionRequestDto update){
        bookEditionService.updateEdition(idEdition,update);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/edition/{id}")
    public ResponseEntity deleteEdition(@PathVariable("id") int idEdition){
        bookEditionService.deleteEdition(idEdition);
        return ResponseEntity.noContent().build();
    }
}
