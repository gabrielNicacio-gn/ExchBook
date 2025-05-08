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
    @Mapping(target = "exchangeOffer", source = "exchangeOffer")
    Exchange toExchange(CreateExchangeRequestDto createExchange, ExchangeOffer exchangeOffer);
    ExchangeDto toExchangeDto(Exchange exchange);
}
