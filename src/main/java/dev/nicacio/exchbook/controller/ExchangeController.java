package dev.nicacio.exchbook.controller;

import dev.nicacio.exchbook.dtos.request.CreateExchangeRequestDto;
import dev.nicacio.exchbook.dtos.response.ExchangeDto;
import dev.nicacio.exchbook.services.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ExchangeController {
    private final ExchangeService exchangeService;
    @PostMapping("/exchange")
    public ResponseEntity addExchange(@RequestBody CreateExchangeRequestDto create){
        HttpHeaders headers = new HttpHeaders();
        int response = exchangeService.registerExchange(create);
        headers.setLocation(UriComponentsBuilder
                .newInstance()
                .path("/v1/exchange/{id}")
                .buildAndExpand(response)
                .toUri());
        return new ResponseEntity(headers,HttpStatus.CREATED);
    }

    @GetMapping("/exchange/{id}")
    public ResponseEntity<ExchangeDto> returnExchangeById(@PathVariable("id") int idExchange) throws ChangeSetPersister.NotFoundException {
        ExchangeDto response = exchangeService.getExchangeById(idExchange);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
