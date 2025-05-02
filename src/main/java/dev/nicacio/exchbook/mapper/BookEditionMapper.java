package dev.nicacio.exchbook.mapper;

import dev.nicacio.exchbook.dtos.request.CreateBookEditionRequestDto;
import dev.nicacio.exchbook.dtos.response.BookEditionDto;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.BookEdition;
import dev.nicacio.exchbook.repository.BookRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface BookEditionMapper {
    @Mapping(target = "book", source = "book")
    BookEdition toBookEdition(CreateBookEditionRequestDto createBookEdition,Book book);
    BookEditionDto toBookEditionDto (BookEdition bookEdition);
}
