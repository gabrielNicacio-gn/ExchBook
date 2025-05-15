package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateBookRequestDto;
import dev.nicacio.exchbook.dtos.request.CreateExchangeOfferRequestDto;
import dev.nicacio.exchbook.dtos.response.BookCopyDto;
import dev.nicacio.exchbook.dtos.response.BookDto;
import dev.nicacio.exchbook.dtos.response.ExchangeOfferDto;
import dev.nicacio.exchbook.enums.Condition;
import dev.nicacio.exchbook.enums.StatusExchangeOffer;
import dev.nicacio.exchbook.mapper.ExchangeOfferMapper;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.BookCopy;
import dev.nicacio.exchbook.models.ExchangeOffer;
import dev.nicacio.exchbook.repository.BookCopyRepository;
import dev.nicacio.exchbook.repository.BookRepository;
import dev.nicacio.exchbook.repository.ExchangeOfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExchangeOfferServiceTest {
    @Mock
    private ExchangeOfferRepository exchangeOfferRepository;
    @Mock
    private  BookRepository bookRepository;
    @Mock
    private BookCopyRepository bookCopyRepository;
    @Mock
    private ExchangeOfferMapper exchangeOfferMapper;

    @InjectMocks
    private ExchangeOfferService exchangeOfferService;
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void shouldRegisterAnExchangeOffer(){
        CreateExchangeOfferRequestDto create = new CreateExchangeOfferRequestDto(1,2);

        Optional<BookCopy> copyOffered = Optional.of(new BookCopy());
        copyOffered.get().setIdCopy(1);
        copyOffered.get().setCondition(Condition.NOVO);

        Optional<Book> bookDesired = Optional.of(new Book());
        bookDesired.get().setIdBook(2);
        bookDesired.get().setTitle("My Second Book");

        ExchangeOffer savedExchangeOffer = new ExchangeOffer();
        savedExchangeOffer.setIdExchangeOffer(1);
        savedExchangeOffer.setStatusExchangeOffer(StatusExchangeOffer.OPEN);
        savedExchangeOffer.setBookDesired(bookDesired.get());
        savedExchangeOffer.setCopyOffered(copyOffered.get());

        when(exchangeOfferMapper.toExchangeOffer(create,bookDesired.get(),copyOffered.get())).thenReturn(savedExchangeOffer);
        when(bookRepository.findById(2)).thenReturn(bookDesired);
        when(bookCopyRepository.findById(1)).thenReturn(copyOffered);
        when(exchangeOfferRepository.save(ArgumentMatchers.any(ExchangeOffer.class))).thenReturn(savedExchangeOffer);

        int idCreatedExchangeOffer = exchangeOfferService.registerExchangeOffer(create);

        verify(bookRepository,times(1)).findById(2);
        verify(bookCopyRepository,times(1)).findById(1);
        verify(exchangeOfferRepository,times(1)).save(ArgumentMatchers.any(ExchangeOffer.class));

        assertEquals(savedExchangeOffer.getIdExchangeOffer(),idCreatedExchangeOffer);
    }
    @Test
    public void shouldFailToRegisterAnOfferAndThrowIllegalArgumentExceptionWithMessageBookNotFound(){
        CreateExchangeOfferRequestDto create = new CreateExchangeOfferRequestDto(99,98);

        when(bookRepository.findById(98)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()->
               exchangeOfferService.registerExchangeOffer(create));

        assertEquals("Book not found, can't create a exchange offer",ex.getMessage());
    }

    @Test
    public void shouldFailToRegisterAnOfferAndThrowIllegalArgumentExceptionWithMessageCopyNotFound(){
        CreateExchangeOfferRequestDto create = new CreateExchangeOfferRequestDto(99,98);

        when(bookRepository.findById(98)).thenReturn(Optional.of(new Book()));
        when(bookCopyRepository.findById(99)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()->
                exchangeOfferService.registerExchangeOffer(create));

        assertEquals("Copy not found, can't create a exchange offer",ex.getMessage());
    }

    @Test
    public void shouldGetExchangeOfferById() throws ChangeSetPersister.NotFoundException {
        int idExchangeOffer = 1;

        Book bookDesired = new Book();
        bookDesired.setIdBook(2);
        bookDesired.setTitle("My Second Book");

        BookCopy bookCopyOffered = new BookCopy();
        bookCopyOffered.setIdCopy(1);
        bookCopyOffered.setCondition(Condition.NOVO);

        BookDto bookDto = new BookDto(bookDesired.getIdBook(),bookDesired.getTitle(),new ArrayList<>());
        BookCopyDto bookCopyDto = new BookCopyDto(bookCopyOffered.getIdCopy(),bookCopyOffered.getCondition(),null);

        ExchangeOffer exchangeOffer = new ExchangeOffer();
        exchangeOffer.setIdExchangeOffer(1);
        exchangeOffer.setStatusExchangeOffer(StatusExchangeOffer.OPEN);
        exchangeOffer.setBookDesired(bookDesired);
        exchangeOffer.setCopyOffered(bookCopyOffered);

        ExchangeOfferDto expectedExchangeOfferDto = new ExchangeOfferDto(exchangeOffer.getIdExchangeOffer(),bookCopyDto,bookDto,
                exchangeOffer.getDateOfOffer(),exchangeOffer.getStatusExchangeOffer());

        when(exchangeOfferRepository.findByIdAndIsDeletedFalse(idExchangeOffer)).thenReturn(Optional.of(exchangeOffer));
        when(exchangeOfferMapper.toExchangeOfferDto(exchangeOffer)).thenReturn(expectedExchangeOfferDto);

        ExchangeOfferDto exchangeOfferDto = exchangeOfferService.findExchangeOfferById(idExchangeOffer);

        verify(exchangeOfferRepository,times(1)).findByIdAndIsDeletedFalse(idExchangeOffer);

        assertEquals(expectedExchangeOfferDto,exchangeOfferDto);
    }

    @Test
    public void shouldGetAllExchangeOffer_whenStatusIsNull(){
        ExchangeOffer exchangeOfferA = new ExchangeOffer();
        exchangeOfferA.setIdExchangeOffer(1);
        exchangeOfferA.setStatusExchangeOffer(StatusExchangeOffer.OPEN);

        ExchangeOffer exchangeOfferB = new ExchangeOffer();
        exchangeOfferB.setIdExchangeOffer(2);
        exchangeOfferB.setStatusExchangeOffer(StatusExchangeOffer.CLOSED);
        List<ExchangeOffer> offersList = List.of(exchangeOfferA,exchangeOfferB);

        ExchangeOfferDto exchangeOfferADto = new ExchangeOfferDto(exchangeOfferA.getIdExchangeOffer(),null,null,
                exchangeOfferA.getDateOfOffer(),exchangeOfferA.getStatusExchangeOffer());
        ExchangeOfferDto exchangeOfferBDto = new ExchangeOfferDto(exchangeOfferB.getIdExchangeOffer(),null,null,
                exchangeOfferB.getDateOfOffer(),exchangeOfferB.getStatusExchangeOffer());

        List<ExchangeOfferDto> expectedResult = List.of(exchangeOfferADto,exchangeOfferBDto);

        when(exchangeOfferRepository.findAllByIsDeletedFalse()).thenReturn(offersList);
        when(exchangeOfferMapper.toExchangeOfferDto(exchangeOfferA)).thenReturn(exchangeOfferADto);
        when(exchangeOfferMapper.toExchangeOfferDto(exchangeOfferB)).thenReturn(exchangeOfferBDto);

        List<ExchangeOfferDto> result = exchangeOfferService.findAllExchangeOffer(null);

        verify(exchangeOfferRepository,times(1)).findAllByIsDeletedFalse();
        verify(exchangeOfferRepository,never()).findByStatusExchangeOffer(any());
        assertEquals(expectedResult,result);
    }
    @Test
    public void shouldGetAllExchangeOffer_whenStatusIsProvide(){
        ExchangeOffer exchangeOfferA = new ExchangeOffer();
        exchangeOfferA.setIdExchangeOffer(1);
        exchangeOfferA.setStatusExchangeOffer(StatusExchangeOffer.OPEN);

        ExchangeOffer exchangeOfferB = new ExchangeOffer();
        exchangeOfferB.setIdExchangeOffer(2);
        exchangeOfferB.setStatusExchangeOffer(StatusExchangeOffer.CLOSED);

        ExchangeOffer exchangeOfferC = new ExchangeOffer();
        exchangeOfferC.setIdExchangeOffer(3);
        exchangeOfferC.setStatusExchangeOffer(StatusExchangeOffer.OPEN);

        List<ExchangeOffer> offersList = List.of(exchangeOfferA,exchangeOfferC);

        ExchangeOfferDto exchangeOfferADto = new ExchangeOfferDto(exchangeOfferA.getIdExchangeOffer(),null,null,
                exchangeOfferA.getDateOfOffer(),exchangeOfferA.getStatusExchangeOffer());
        ExchangeOfferDto exchangeOfferCDto = new ExchangeOfferDto(exchangeOfferC.getIdExchangeOffer(),null,null,
                exchangeOfferC.getDateOfOffer(),exchangeOfferC.getStatusExchangeOffer());

        List<ExchangeOfferDto> expectedResult = List.of(exchangeOfferADto,exchangeOfferCDto);

        when(exchangeOfferRepository.findByStatusExchangeOffer(StatusExchangeOffer.OPEN)).thenReturn(offersList);
        when(exchangeOfferMapper.toExchangeOfferDto(exchangeOfferA)).thenReturn(exchangeOfferADto);
        when(exchangeOfferMapper.toExchangeOfferDto(exchangeOfferC)).thenReturn(exchangeOfferCDto);

        List<ExchangeOfferDto> result = exchangeOfferService.findAllExchangeOffer(StatusExchangeOffer.OPEN);

        verify(exchangeOfferRepository,never()).findAllByIsDeletedFalse();
        verify(exchangeOfferRepository,times(1)).findByStatusExchangeOffer(any());
        assertEquals(expectedResult,result);
    }
    @Test
    public void shouldDeleteAnExchangeOffer(){
        int idOffer = 1;

        ExchangeOffer exchangeOffer = new ExchangeOffer();
        exchangeOffer.setIdExchangeOffer(1);
        exchangeOffer.setStatusExchangeOffer(StatusExchangeOffer.OPEN);

        when(exchangeOfferRepository.findByIdAndIsDeletedFalse(idOffer)).thenReturn(Optional.of(exchangeOffer));
        when(exchangeOfferRepository.save(any())).thenReturn(any(ExchangeOffer.class));

        exchangeOfferService.deleteExchangeOffer(idOffer);

        verify(exchangeOfferRepository,times(1)).save(any(ExchangeOffer.class));
        verify(exchangeOfferRepository,times(1)).findByIdAndIsDeletedFalse(idOffer);

        assertTrue(exchangeOffer.isDeleted());
    }
}