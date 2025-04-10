package dev.nicacio.exchbook.services;


import dev.nicacio.exchbook.dtos.request.CreateExchangeOfferDto;
import dev.nicacio.exchbook.dtos.response.ExchangeOfferDto;
import dev.nicacio.exchbook.mapper.ExchangeOfferMapper;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.BookCopy;
import dev.nicacio.exchbook.models.ExchangeOffer;
import dev.nicacio.exchbook.repository.BookCopyRepository;
import dev.nicacio.exchbook.repository.BookRepository;
import dev.nicacio.exchbook.repository.ExchangeOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeOfferService {
    private final ExchangeOfferRepository exchangeOfferRepository;
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final ExchangeOfferMapper exchangeOfferMapper;

    public int registerExchangeOffer(CreateExchangeOfferDto createExchangeOffer){
        ExchangeOffer offer = exchangeOfferMapper.toExchangeOffer(createExchangeOffer,bookRepository,bookCopyRepository);
        ExchangeOffer savedOffer = exchangeOfferRepository.save(offer);
        return savedOffer.getIdExchangeOffer();
    }
}
