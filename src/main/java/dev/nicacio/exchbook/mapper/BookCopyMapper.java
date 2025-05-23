package dev.nicacio.exchbook.mapper;

import dev.nicacio.exchbook.dtos.request.CreateBookCopyRequestDto;
import dev.nicacio.exchbook.dtos.request.UpdateBookCopyRequestDto;
import dev.nicacio.exchbook.dtos.response.BookCopyDto;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.BookCopy;
import dev.nicacio.exchbook.repository.BookRepository;
import jakarta.persistence.Column;
import org.mapstruct.*;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {

    @Mapping(target = "book", source = "book")
    BookCopy toBookCopy(CreateBookCopyRequestDto createBookCopy, Book book);
    BookCopyDto toBookCopyDto(BookCopy copy);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBookCopyFromDto(UpdateBookCopyRequestDto update, @MappingTarget BookCopy bookCopy);
}
