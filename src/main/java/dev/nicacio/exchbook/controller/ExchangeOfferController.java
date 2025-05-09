package dev.nicacio.exchbook.controller;

import dev.nicacio.exchbook.dtos.request.CreateExchangeOfferRequestDto;
import dev.nicacio.exchbook.dtos.response.ExchangeOfferDto;
import dev.nicacio.exchbook.enums.StatusExchangeOffer;
import dev.nicacio.exchbook.exceptions.ResourceNotFoundException;
import dev.nicacio.exchbook.services.ExchangeOfferService;
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
    public ResponseEntity<ExchangeOfferDto> getExchangeOfferById(@PathVariable("id") int idExchangeOffer) throws ResourceNotFoundException {
        ExchangeOfferDto response = exchangeOfferService.findExchangeOfferById(idExchangeOffer);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/offers")
    public ResponseEntity<List<ExchangeOfferDto>> getAllOffers(@RequestParam(required = false) StatusExchangeOffer statusExchangeOffer){
        List<ExchangeOfferDto> response = exchangeOfferService.findAllExchangeOffer(statusExchangeOffer);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @DeleteMapping("/offer/{id}")
    public ResponseEntity deleteOffer(@PathVariable("id") int idExchangeOffer){
        exchangeOfferService.deleteExchangeOffer(idExchangeOffer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
