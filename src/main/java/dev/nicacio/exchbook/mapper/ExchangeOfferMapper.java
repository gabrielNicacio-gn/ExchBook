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

    @Mapping(target = "bookDesired", source = "idBookDesired")
    @Mapping(target = "copyOffered", source = "idCopyOffered")
    ExchangeOffer toExchangeOffer(CreateExchangeOfferRequestDto createExchangeOffer, @Context BookRepository bookRepository
            , @Context BookCopyRepository bookCopyRepository);

    default Book mapIdBookToBook(int idBook,@Context BookRepository bookRepository){
        return bookRepository.findById(idBook)
                .orElseThrow(()-> new IllegalArgumentException("Book or Copy not found, can't create a exchange offer"));
    }
    default BookCopy mapIdBookCopyToBookCopy(int idBookCopy,@Context BookCopyRepository bookCopyRepository){
        return bookCopyRepository.findById(idBookCopy)
                .orElseThrow(()-> new IllegalArgumentException("Book or Copy not found, can't create a exchange offer"));
    }
    ExchangeOfferDto toExchangeOfferDto(ExchangeOffer offer);
}
