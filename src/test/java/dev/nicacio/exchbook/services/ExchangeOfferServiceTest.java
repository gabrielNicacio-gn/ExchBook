package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateExchangeOfferRequestDto;
import dev.nicacio.exchbook.enums.Condition;
import dev.nicacio.exchbook.enums.StatusExchange;
import dev.nicacio.exchbook.mapper.ExchangeOfferMapper;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.BookCopy;
import dev.nicacio.exchbook.models.ExchangeOffer;
import dev.nicacio.exchbook.repository.BookCopyRepository;
import dev.nicacio.exchbook.repository.BookRepository;
import dev.nicacio.exchbook.repository.ExchangeOfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExchangeOfferServiceTest {
    @Mock
    private ExchangeOfferRepository exchangeOfferRepository;
    @Mock
    private  BookRepository bookRepository;
    @Mock
    private BookCopyRepository bookCopyRepository;
    private final ExchangeOfferMapper exchangeOfferMapper = Mappers.getMapper(ExchangeOfferMapper.class);

    @InjectMocks
    private ExchangeOfferService exchangeOfferService;
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        exchangeOfferService = new ExchangeOfferService(exchangeOfferRepository,bookRepository,bookCopyRepository,exchangeOfferMapper);
    }
    @Test
    public void shouldRegisterAnExchangeOffer(){
        CreateExchangeOfferRequestDto create = new CreateExchangeOfferRequestDto(1,2);

        Optional<BookCopy> bookCopyOffered = Optional.of(new BookCopy());
        bookCopyOffered.get().setIdCopy(1);
        bookCopyOffered.get().setCondition(Condition.NOVO);

        Optional<Book> bookDesired = Optional.of(new Book());
        bookDesired.get().setIdBook(2);
        bookDesired.get().setTitle("My Second Book");

        ExchangeOffer savedExchangeOffer = new ExchangeOffer();
        savedExchangeOffer.setIdExchangeOffer(1);
        savedExchangeOffer.setStatusExchange(StatusExchange.ABERTA);
        savedExchangeOffer.setBookDesired(bookDesired.get());
        savedExchangeOffer.setCopyOffered(bookCopyOffered.get());

        when(bookRepository.findById(2)).thenReturn(bookDesired);
        when(bookCopyRepository.findById(1)).thenReturn(bookCopyOffered);
        when(exchangeOfferRepository.save(ArgumentMatchers.any(ExchangeOffer.class))).thenReturn(savedExchangeOffer);

        int idCreatedExchangeOffer = exchangeOfferService.registerExchangeOffer(create);

        verify(bookRepository,times(1)).findById(2);
        verify(bookCopyRepository,times(1)).findById(1);
        verify(exchangeOfferRepository,times(1)).save(ArgumentMatchers.any(ExchangeOffer.class));

        assertEquals(savedExchangeOffer.getIdExchangeOffer(),idCreatedExchangeOffer);
    }

}