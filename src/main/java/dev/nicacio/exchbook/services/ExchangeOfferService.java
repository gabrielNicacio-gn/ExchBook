package dev.nicacio.exchbook.services;


import dev.nicacio.exchbook.dtos.request.CreateExchangeOfferRequestDto;
import dev.nicacio.exchbook.dtos.response.ExchangeOfferDto;
import dev.nicacio.exchbook.enums.StatusExchangeOffer;
import dev.nicacio.exchbook.exceptions.ResourceNotFoundException;
import dev.nicacio.exchbook.mapper.ExchangeOfferMapper;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.BookCopy;
import dev.nicacio.exchbook.models.ExchangeOffer;
import dev.nicacio.exchbook.repository.BookCopyRepository;
import dev.nicacio.exchbook.repository.BookRepository;
import dev.nicacio.exchbook.repository.ExchangeOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeOfferService {
    private final ExchangeOfferRepository exchangeOfferRepository;
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final ExchangeOfferMapper exchangeOfferMapper;

    public int registerExchangeOffer(CreateExchangeOfferRequestDto createExchangeOffer){

        Book bookDesired = bookRepository.findById(createExchangeOffer.idBookDesired())
                .orElseThrow(()-> new IllegalArgumentException("Book not found, can't create a exchange offer"));

        BookCopy copyOffered = bookCopyRepository.findById(createExchangeOffer.idCopyOffered())
                .orElseThrow(()-> new IllegalArgumentException("Copy not found, can't create a exchange offer"));

        ExchangeOffer offer = exchangeOfferMapper.toExchangeOffer(createExchangeOffer,bookDesired,copyOffered);
        ExchangeOffer savedOffer = exchangeOfferRepository.save(offer);
        return savedOffer.getIdExchangeOffer();
    }

    public ExchangeOfferDto findExchangeOfferById(int idExchangeOffer) throws ResourceNotFoundException {
        ExchangeOffer exchangeOffer = exchangeOfferRepository.findById(idExchangeOffer)
                .orElseThrow(()-> new ResourceNotFoundException("Exchange Offer not found"));
        return exchangeOfferMapper.toExchangeOfferDto(exchangeOffer);
    }

    public List<ExchangeOfferDto> findAllExchangeOffer(StatusExchangeOffer statusOffer){
        List<ExchangeOffer> list = statusOffer==null
                ? exchangeOfferRepository.findAll()
                : exchangeOfferRepository.findByStatusExchangeOffer(statusOffer);

        return list.stream().map(exchangeOfferMapper::toExchangeOfferDto).toList();
    }
    
    public void deleteExchangeOffer(int idOffer){
        ExchangeOffer exchangeOffer = exchangeOfferRepository.findById(idOffer)
                .orElseThrow(()-> new IllegalArgumentException("Exchange Offer not found"));
        exchangeOffer.makeAsDeleted();
        exchangeOfferRepository.save(exchangeOffer);
    }
}
