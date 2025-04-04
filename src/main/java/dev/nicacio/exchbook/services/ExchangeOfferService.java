package dev.nicacio.exchbook.services;


import dev.nicacio.exchbook.dtos.request.CreateExchangeOfferDto;
import dev.nicacio.exchbook.dtos.response.ExchangeOfferDto;
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

    public ExchangeOfferDto registerExchangeOffer(CreateExchangeOfferDto createExchangeOfferDto){
        Optional<BookCopy> bookCopyOptional = bookCopyRepository.findById(createExchangeOfferDto.idCopyOffered());
        Optional<Book> bookOptional = bookRepository.findById(createExchangeOfferDto.idBookDesired());

        if(bookOptional.isEmpty() || bookCopyOptional.isEmpty()){
            throw new IllegalArgumentException("Book or Copy not found, can't create a exchange offer");
        }
        Book book = bookOptional.get();
        BookCopy copy = bookCopyOptional.get();

        ExchangeOffer offer = new ExchangeOffer();
        offer.setCopyOffered(copy);
        offer.setBookDesired(book);
        ExchangeOffer savedOffer = exchangeOfferRepository.save(offer);
        return new ExchangeOfferDto(savedOffer.getIdExchangeOffer(),
                savedOffer.getCopyOffered().getBook().getTitle()
                ,savedOffer.getBookDesired().getTitle(),
               savedOffer.getDateOfOffer(),
                savedOffer.getStatusExchange());
    }
}
