package dev.nicacio.exchbook.mapper;

import dev.nicacio.exchbook.dtos.request.CreateExchangeRequestDto;
import dev.nicacio.exchbook.dtos.response.ExchangeDto;
import dev.nicacio.exchbook.models.Exchange;
import dev.nicacio.exchbook.models.ExchangeOffer;
import dev.nicacio.exchbook.repository.ExchangeOfferRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExchangeMapper {
    @Mapping(target = "exchangeOffer", source = "idExchangeOffer")
    Exchange toExchange(CreateExchangeRequestDto createExchange, @Context ExchangeOfferRepository exchangeOfferRepository);

    default ExchangeOffer mapIdExchangeOfferToExchangeOffer( int idExchangeOffer,@Context ExchangeOfferRepository exchangeOfferRepository){
        return exchangeOfferRepository.findById(idExchangeOffer)
                .orElseThrow(()-> new IllegalArgumentException("Exchange not found, can't create a exchange"));
    }

    ExchangeDto toExchangeDto(Exchange exchange);
}
