package dev.nicacio.exchbook.controller;

import dev.nicacio.exchbook.dtos.request.CreateExchangeOfferDto;
import dev.nicacio.exchbook.dtos.response.ExchangeOfferDto;
import dev.nicacio.exchbook.services.ExchangeOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ExchangeOfferController {
    private final ExchangeOfferService exchangeOfferService;

    @PostMapping("/offer")
    public ResponseEntity<ExchangeOfferDto> addExchangeOffer(@RequestBody CreateExchangeOfferDto create){
        ExchangeOfferDto response = exchangeOfferService.registerExchangeOffer(create);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
