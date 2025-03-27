package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateBookCopyRequestDto;
import dev.nicacio.exchbook.dtos.response.CreateBookCopyResponseDto;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.BookCopy;
import dev.nicacio.exchbook.repository.BookRepository;
import dev.nicacio.exchbook.repository.CopyBookRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookCopyService {

    private final BookRepository bookRepository;
    private final CopyBookRepository copyBookRepository;
    public CreateBookCopyResponseDto registerBookCopy(CreateBookCopyRequestDto createBook){

        CreateBookCopyResponseDto response = new CreateBookCopyResponseDto();
        Optional<Book> bookOptional = bookRepository.findById(createBook.IdBook());

        if(bookOptional.isEmpty()){
            response.addErros("Book not found, it is not possible create a copy");
            return response;
        }

        Book book = bookOptional.get();
        BookCopy newCopy = new BookCopy();
        newCopy.setBook(book);
        newCopy.setCondition(createBook.condition());
        copyBookRepository.save(newCopy);

        response.setIdCopy(newCopy.getIdCopy());
        response.setCondition(newCopy.getCondition());
        response.setTitle(book.getTitle());
        return response;
    }
}
