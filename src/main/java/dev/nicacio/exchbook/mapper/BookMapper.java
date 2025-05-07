package dev.nicacio.exchbook.mapper;

import dev.nicacio.exchbook.dtos.request.CreateBookRequestDto;
import dev.nicacio.exchbook.dtos.request.UpdateBookRequestDto;
import dev.nicacio.exchbook.dtos.response.BookDto;
import dev.nicacio.exchbook.models.Author;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.BookCopy;
import dev.nicacio.exchbook.repository.AuthorRepository;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    //@Mapping(target = "authors", source = "authorIds")
    Book toBook (CreateBookRequestDto createBook, List<Author> authors);
    BookDto toBookDto(Book book);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBookFromDto(UpdateBookRequestDto updateDto, @MappingTarget Book book);
}
