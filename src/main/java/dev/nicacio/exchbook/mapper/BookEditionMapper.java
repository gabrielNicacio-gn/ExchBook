package dev.nicacio.exchbook.mapper;

import dev.nicacio.exchbook.dtos.request.CreateBookEditionRequestDto;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.BookEdition;
import dev.nicacio.exchbook.repository.BookRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface BookEditionMapper {
    @Mapping(target = "book", source = "idBook")
    BookEdition toBookEdition(CreateBookEditionRequestDto createBookEdition,@Context BookRepository bookRepository);

    default Book mapIdBooktoBook(int idBook, @Context BookRepository bookRepository){
        Optional<Book> optionalBook = bookRepository.findById(idBook);
        if(optionalBook.isEmpty()){
            throw new IllegalArgumentException("No Book found, can't create a edition");
        }
        return  optionalBook.get();
    }
}
