package dev.nicacio.exchbook.mapper;

import dev.nicacio.exchbook.dtos.request.CreateBookCopyRequestDto;
import dev.nicacio.exchbook.dtos.response.BookCopyDto;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.BookCopy;
import dev.nicacio.exchbook.repository.BookRepository;
import jakarta.persistence.Column;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {
    @Mapping(target = "book", source = "idBook")
    BookCopy toBookCopy(CreateBookCopyRequestDto createBookCopy, @Context BookRepository bookRepository);

    default Book mapBookIdtoBook(int idBook,@Context BookRepository bookRepository){
        Optional<Book> bookOptional = bookRepository.findById(idBook);
        if(bookOptional.isEmpty()){
            throw new IllegalArgumentException("Book not found, cannot create a copy");
        }
        return bookOptional.get();
    }

    BookCopyDto toBookCopyDto(BookCopy copy);
}
