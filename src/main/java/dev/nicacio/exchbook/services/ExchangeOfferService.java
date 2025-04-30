package dev.nicacio.exchbook.services;


import dev.nicacio.exchbook.dtos.request.CreateExchangeOfferRequestDto;
import dev.nicacio.exchbook.dtos.response.ExchangeOfferDto;
import dev.nicacio.exchbook.enums.StatusExchangeOffer;
import dev.nicacio.exchbook.exceptions.ResourceNotFoundException;
import dev.nicacio.exchbook.mapper.ExchangeOfferMapper;
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
        ExchangeOffer offer = exchangeOfferMapper.toExchangeOffer(createExchangeOffer,bookRepository,bookCopyRepository);
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
}
