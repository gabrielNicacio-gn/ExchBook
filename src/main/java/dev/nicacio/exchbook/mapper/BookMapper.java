package dev.nicacio.exchbook.mapper;

import dev.nicacio.exchbook.dtos.request.CreateBookRequestDto;
import dev.nicacio.exchbook.dtos.response.BookDto;
import dev.nicacio.exchbook.models.Author;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.repository.AuthorRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "authors", source = "authorIds")
    Book toBook (CreateBookRequestDto createBook, @Context AuthorRepository authorRepository);
    default List<Author> mapAuthorsIdsToAuthors(List<Integer> authorIds, @Context AuthorRepository authorRepository){
      List<Author> authors =authorIds != null ? authorRepository.findAllById(authorIds): Collections.emptyList();
      if(authors.isEmpty()){
          throw new IllegalArgumentException("No Author found, can't create a book");
      }
      return authors;
    }
}
