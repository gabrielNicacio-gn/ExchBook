package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateExchangeRequestDto;
import dev.nicacio.exchbook.dtos.response.ExchangeDto;
import dev.nicacio.exchbook.enums.StatusExchangeOffer;
import dev.nicacio.exchbook.exceptions.ResourceNotFoundException;
import dev.nicacio.exchbook.mapper.ExchangeMapper;
import dev.nicacio.exchbook.models.Exchange;
import dev.nicacio.exchbook.models.ExchangeOffer;
import dev.nicacio.exchbook.repository.ExchangeOfferRepository;
import dev.nicacio.exchbook.repository.ExchangeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final ExchangeOfferRepository exchangeOfferRepository;
    private final ExchangeRepository exchangeRepository;
    private final ExchangeMapper exchangeMapper;

    @Transactional
    public int registerExchange(CreateExchangeRequestDto create){
        ExchangeOffer exchangeOffer = exchangeOfferRepository.findById(create.idExchangeOffer())
                .orElseThrow(()-> new IllegalArgumentException("Exchange not found, can't create a exchange"));

        Exchange exchange = exchangeMapper.toExchange(create,exchangeOffer);
        Exchange savedExchange = exchangeRepository.save(exchange);

       ExchangeOffer currentExchangeOffer = exchangeOfferRepository
               .findById(exchange.getExchangeOffer().getIdExchangeOffer()).get();
       currentExchangeOffer.setStatusExchangeOffer(StatusExchangeOffer.CLOSED);
       exchangeOfferRepository.save(currentExchangeOffer);

        return savedExchange.getIdExchange();
    }
    public ExchangeDto findExchangeById(int idExchange) throws ResourceNotFoundException {
        Exchange exchange = exchangeRepository.findById(idExchange)
                .orElseThrow(()-> new ResourceNotFoundException("Exchange not found"));
        return exchangeMapper.toExchangeDto(exchange);
    }

    public List<ExchangeDto> findAllExchanges(){
        return exchangeRepository.findAll().stream().map(exchangeMapper::toExchangeDto).toList();
    }

    public void deleteExchange(int idExchange){
        Exchange exchange = exchangeRepository.findById(idExchange)
                .orElseThrow(()-> new IllegalArgumentException("Exchange not found"));
        exchange.makeAsDeleted();
        exchangeRepository.save(exchange);
    }

}
