package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateBookCopyRequestDto;
import dev.nicacio.exchbook.dtos.response.CreateBookCopyResponseDto;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.BookCopy;
import dev.nicacio.exchbook.repository.BookRepository;
import dev.nicacio.exchbook.repository.BookCopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookCopyService {

    private final BookRepository bookRepository;
    private final BookCopyRepository copyBookRepository;
    public CreateBookCopyResponseDto registerBookCopy(CreateBookCopyRequestDto createBook){

        Optional<Book> bookOptional = bookRepository.findById(createBook.idBook());

        if(bookOptional.isEmpty()){
            throw new IllegalArgumentException("Book not found, cannot create a copy");
        }

        Book book = bookOptional.get();
        BookCopy newCopy = new BookCopy();
        newCopy.setBook(book);
        newCopy.setCondition(createBook.condition());
        BookCopy savedCopy = copyBookRepository.save(newCopy);
        return new CreateBookCopyResponseDto(savedCopy.getIdCopy(),savedCopy.getCondition(),book.getTitle());
    }
}
