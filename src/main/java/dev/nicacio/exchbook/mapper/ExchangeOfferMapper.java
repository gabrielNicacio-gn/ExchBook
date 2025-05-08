package dev.nicacio.exchbook.mapper;

import dev.nicacio.exchbook.dtos.request.CreateExchangeOfferRequestDto;
import dev.nicacio.exchbook.dtos.response.ExchangeOfferDto;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.BookCopy;
import dev.nicacio.exchbook.models.ExchangeOffer;
import dev.nicacio.exchbook.repository.BookCopyRepository;
import dev.nicacio.exchbook.repository.BookRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExchangeOfferMapper {

    @Mapping(target = "bookDesired", source = "bookDesired")
    @Mapping(target = "copyOffered", source = "copyOffered")
    ExchangeOffer toExchangeOffer(CreateExchangeOfferRequestDto createExchangeOffer, Book bookDesired, BookCopy copyOffered);
    ExchangeOfferDto toExchangeOfferDto(ExchangeOffer offer);
}
