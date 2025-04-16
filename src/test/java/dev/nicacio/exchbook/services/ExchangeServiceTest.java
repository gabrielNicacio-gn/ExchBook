package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateExchangeRequestDto;
import dev.nicacio.exchbook.enums.StatusExchange;
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

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExchangeServiceTest {
    @Mock
    private  ExchangeOfferRepository exchangeOfferRepository;
    @Mock
    private  ExchangeRepository exchangeRepository;
    private final ExchangeMapper exchangeMapper = Mappers.getMapper(ExchangeMapper.class);
    @InjectMocks
    private ExchangeService exchangeService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        exchangeService = new ExchangeService(exchangeOfferRepository,exchangeRepository,exchangeMapper);
    }
    @Test
    public void shouldRegisterAnExchange(){
        CreateExchangeRequestDto create = new CreateExchangeRequestDto(1);
        Optional<ExchangeOffer> exchangeOffer = Optional.of(new ExchangeOffer());
        exchangeOffer.get().setIdExchangeOffer(1);

        Exchange exchange = new Exchange();
        exchange.setIdExchange(1);
        exchange.setExchangeOffer(exchangeOffer.get());

        when(exchangeOfferRepository.findById(1)).thenReturn(exchangeOffer);
        when(exchangeRepository.save(any(Exchange.class))).thenReturn(exchange);
        when(exchangeOfferRepository.save(any(ExchangeOffer.class))).thenReturn(exchangeOffer.get());

        int idCreatedExchange = exchangeService.registerExchange(create);

        verify(exchangeOfferRepository,times(2)).findById(1);
        verify(exchangeRepository,times(1)).save(any(Exchange.class));
        verify(exchangeOfferRepository,times(1)).save(any(ExchangeOffer.class));

        assertEquals(exchange.getIdExchange(),idCreatedExchange);
        assertEquals(StatusExchange.ACEITA,exchangeOffer.get().getStatusExchange());
    }


}