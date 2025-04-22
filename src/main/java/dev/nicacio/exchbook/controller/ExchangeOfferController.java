package dev.nicacio.exchbook.controller;

import dev.nicacio.exchbook.dtos.request.CreateExchangeOfferRequestDto;
import dev.nicacio.exchbook.dtos.response.ExchangeOfferDto;
import dev.nicacio.exchbook.services.ExchangeOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ExchangeOfferController {
    private final ExchangeOfferService exchangeOfferService;

    @PostMapping("/offer")
    public ResponseEntity<ExchangeOfferDto> addExchangeOffer(@RequestBody CreateExchangeOfferRequestDto create){
        HttpHeaders headers = new HttpHeaders();
        int response = exchangeOfferService.registerExchangeOffer(create);

        headers.setLocation(UriComponentsBuilder
                .newInstance()
                .path("/v1/offer/{id}")
                .buildAndExpand(response)
                .toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/offer/{id}")
    public ResponseEntity<ExchangeOfferDto> returnExchangeOfferById(@PathVariable("id") int idExchangeOffer) throws ChangeSetPersister.NotFoundException {
        ExchangeOfferDto response = exchangeOfferService.getExchangeOfferById(idExchangeOffer);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
