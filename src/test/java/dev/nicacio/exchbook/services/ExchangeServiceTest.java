package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateExchangeRequestDto;
import dev.nicacio.exchbook.dtos.response.ExchangeDto;
import dev.nicacio.exchbook.dtos.response.ExchangeOfferDto;
import dev.nicacio.exchbook.enums.StatusExchangeOffer;
import dev.nicacio.exchbook.mapper.ExchangeMapper;
import dev.nicacio.exchbook.models.Exchange;
import dev.nicacio.exchbook.models.ExchangeOffer;
import dev.nicacio.exchbook.repository.ExchangeOfferRepository;
import dev.nicacio.exchbook.repository.ExchangeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExchangeServiceTest {
    @Mock
    private  ExchangeOfferRepository exchangeOfferRepository;
    @Mock
    private  ExchangeRepository exchangeRepository;
    @Mock
    private ExchangeMapper exchangeMapper;
    @InjectMocks
    private ExchangeService exchangeService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void shouldRegisterAnExchange(){
        CreateExchangeRequestDto create = new CreateExchangeRequestDto(1);
        Optional<ExchangeOffer> exchangeOffer = Optional.of(new ExchangeOffer());
        exchangeOffer.get().setIdExchangeOffer(1);

        Exchange exchange = new Exchange();
        exchange.setIdExchange(1);
        exchange.setExchangeOffer(exchangeOffer.get());

        when(exchangeMapper.toExchange(create,exchangeOffer.get())).thenReturn(exchange);
        when(exchangeOfferRepository.findById(1)).thenReturn(exchangeOffer);
        when(exchangeRepository.save(any(Exchange.class))).thenReturn(exchange);
        when(exchangeOfferRepository.save(any(ExchangeOffer.class))).thenReturn(exchangeOffer.get());

        int idCreatedExchange = exchangeService.registerExchange(create);

        verify(exchangeOfferRepository,times(2)).findById(1);
        verify(exchangeRepository,times(1)).save(any(Exchange.class));
        verify(exchangeOfferRepository,times(1)).save(any(ExchangeOffer.class));

        assertEquals(exchange.getIdExchange(),idCreatedExchange);
        assertEquals(StatusExchangeOffer.CLOSED,exchangeOffer.get().getStatusExchangeOffer());
    }

    @Test
    public void shouldGetExchangeById() throws ChangeSetPersister.NotFoundException {

        int idExchange = 1;
        ExchangeOffer exchangeOffer = new ExchangeOffer();
        exchangeOffer.setIdExchangeOffer(1);

        Optional<Exchange> exchange = Optional.of(new Exchange());
        exchange.get().setIdExchange(1);
        exchange.get().setExchangeOffer(exchangeOffer);

        ExchangeOfferDto exchangeOfferDto = new ExchangeOfferDto(exchangeOffer.getIdExchangeOffer(),null,null,exchangeOffer.getDateOfOffer()
                ,exchangeOffer.getStatusExchangeOffer());
        ExchangeDto expectedExchangeDto = new ExchangeDto(exchange.get().getIdExchange(),exchange.get().getDateOfExchange(),exchangeOfferDto);

        when(exchangeRepository.findById(idExchange)).thenReturn(exchange);

        ExchangeDto exchangeDto = exchangeService.findExchangeById(idExchange);

        verify(exchangeRepository,times(1)).findById(idExchange);

        assertEquals(expectedExchangeDto,exchangeDto);
    }

    @Test
    public void shouldGetAllExchanges(){
        Exchange exchangeA = new Exchange();
        exchangeA.setIdExchange(1);
        exchangeA.setExchangeOffer(new ExchangeOffer());

        Exchange exchangeB = new Exchange();
        exchangeB.setIdExchange(2);
        exchangeB.setExchangeOffer(new ExchangeOffer());

        List<Exchange> exchangeList = List.of(exchangeA,exchangeB);

        ExchangeDto exchangeADto = exchangeMapper.toExchangeDto(exchangeA);
        ExchangeDto exchangeBDto = exchangeMapper.toExchangeDto(exchangeB);

        List<ExchangeDto> expectedResult = List.of(exchangeADto,exchangeBDto);

        when(exchangeRepository.findAll()).thenReturn(exchangeList);

        List<ExchangeDto> result = exchangeService.findAllExchanges();

        verify(exchangeRepository,times(1)).findAll();
        assertEquals(expectedResult,result);
    }

    @Test
    public void shouldDeletedAnExchange(){
        int idExchange = 1;

        Exchange exchange = new Exchange();
        exchange.setIdExchange(1);
        exchange.setExchangeOffer(new ExchangeOffer());

        when(exchangeRepository.findById(1)).thenReturn(Optional.of(exchange));
        when(exchangeRepository.save(any(Exchange.class))).thenReturn(exchange);

        exchangeService.deleteExchange(idExchange);

        verify(exchangeRepository,times(1)).findById(1);
        verify(exchangeRepository,times(1)).save(any(Exchange.class));

        assertTrue(exchange.isDeleted());
    }

}