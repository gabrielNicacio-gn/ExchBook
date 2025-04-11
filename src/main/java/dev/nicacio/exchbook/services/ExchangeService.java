package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateExchangeRequestDto;
import dev.nicacio.exchbook.enums.StatusExchange;
import dev.nicacio.exchbook.mapper.ExchangeMapper;
import dev.nicacio.exchbook.models.Exchange;
import dev.nicacio.exchbook.models.ExchangeOffer;
import dev.nicacio.exchbook.repository.ExchangeOfferRepository;
import dev.nicacio.exchbook.repository.ExchangeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final ExchangeOfferRepository exchangeOfferRepository;
    private final ExchangeRepository exchangeRepository;
    private final ExchangeMapper exchangeMapper;

    @Transactional
    public int registerExchange(CreateExchangeRequestDto create){
        Exchange exchange = exchangeMapper.toExchange(create,exchangeOfferRepository);
        Exchange savedExchange = exchangeRepository.save(exchange);

       ExchangeOffer currentExchangeOffer = exchangeOfferRepository
               .findById(exchange.getExchangeOffer().getIdExchangeOffer()).get();
       currentExchangeOffer.setStatusExchange(StatusExchange.ACEITA);
       exchangeOfferRepository.save(currentExchangeOffer);

        return savedExchange.getIdExchange();
    }
}
