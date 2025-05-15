package dev.nicacio.exchbook.controller;

import dev.nicacio.exchbook.dtos.request.CreateExchangeOfferRequestDto;
import dev.nicacio.exchbook.dtos.response.ExchangeOfferDto;
import dev.nicacio.exchbook.enums.StatusExchangeOffer;
import dev.nicacio.exchbook.exceptions.ResourceNotFoundException;
import dev.nicacio.exchbook.services.ExchangeOfferService;
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
public class ExchangeOfferController {
    private final ExchangeOfferService exchangeOfferService;

    @PostMapping("/offer")
    public ResponseEntity<ExchangeOfferDto> addExchangeOffer(@RequestBody @Valid CreateExchangeOfferRequestDto create){
        int response = exchangeOfferService.registerExchangeOffer(create);

        URI location = UriComponentsBuilder
                .fromPath("/v1/offer/{id}")
                .buildAndExpand(response)
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @GetMapping("/offer/{id}")
    public ResponseEntity<ExchangeOfferDto> getExchangeOfferById(@PathVariable("id") int idExchangeOffer) throws ResourceNotFoundException {
        ExchangeOfferDto response = exchangeOfferService.findExchangeOfferById(idExchangeOffer);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/offers")
    public ResponseEntity<List<ExchangeOfferDto>> getAllOffers(@RequestParam(required = false) StatusExchangeOffer statusExchangeOffer){
        List<ExchangeOfferDto> response = exchangeOfferService.findAllExchangeOffer(statusExchangeOffer);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/offer/{id}")
    public ResponseEntity deleteOffer(@PathVariable("id") int idExchangeOffer){
        exchangeOfferService.deleteExchangeOffer(idExchangeOffer);
        return ResponseEntity.noContent().build();
    }
}
