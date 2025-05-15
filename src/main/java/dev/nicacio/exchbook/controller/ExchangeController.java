package dev.nicacio.exchbook.controller;

import dev.nicacio.exchbook.dtos.request.CreateExchangeRequestDto;
import dev.nicacio.exchbook.dtos.response.ExchangeDto;
import dev.nicacio.exchbook.exceptions.ResourceNotFoundException;
import dev.nicacio.exchbook.services.ExchangeService;
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
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ExchangeController {
    private final ExchangeService exchangeService;
    @PostMapping("/exchange")
    public ResponseEntity addExchange(@RequestBody CreateExchangeRequestDto create){
        int response = exchangeService.registerExchange(create);
        URI location = UriComponentsBuilder
                .fromPath("/v1/exchange/{id}")
                .buildAndExpand(response)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/exchange/{id}")
    public ResponseEntity<ExchangeDto> getExchangeById(@PathVariable("id") int idExchange) throws ResourceNotFoundException {
        ExchangeDto response = exchangeService.findExchangeById(idExchange);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exchanges")
    public ResponseEntity<List<ExchangeDto>> getAllExchanges(){
        List<ExchangeDto> response = exchangeService.findAllExchanges();
            return ResponseEntity.ok(response);
    }

    @DeleteMapping("/exchange/{id}")
    public ResponseEntity deleteExchange(@PathVariable("id") int idExchange){
        exchangeService.deleteExchange(idExchange);
        return ResponseEntity.noContent().build();
    }
}
