package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateBookCopyRequestDto;
import dev.nicacio.exchbook.dtos.request.UpdateBookCopyRequestDto;
import dev.nicacio.exchbook.dtos.response.BookCopyDto;
import dev.nicacio.exchbook.exceptions.ResourceNotFoundException;
import dev.nicacio.exchbook.mapper.BookCopyMapper;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.BookCopy;
import dev.nicacio.exchbook.repository.BookRepository;
import dev.nicacio.exchbook.repository.BookCopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCopyService {

    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final BookCopyMapper bookCopyMapper;

    public int registerBookCopy(CreateBookCopyRequestDto createBook) {
        Book book = bookRepository.findById(createBook.idBook())
                .orElseThrow(()-> new IllegalArgumentException("Book not found, can't create a copy"));

        BookCopy newCopy = bookCopyMapper.toBookCopy(createBook, book);
        BookCopy savedCopy = bookCopyRepository.save(newCopy);
        return savedCopy.getIdCopy();
    }
    public BookCopyDto findBookCopyById(int idBookCopy) throws ResourceNotFoundException {
        BookCopy bookCopy = bookCopyRepository.findById(idBookCopy)
                .orElseThrow(()-> new ResourceNotFoundException("Book Copy not found"));
        return bookCopyMapper.toBookCopyDto(bookCopy);
    }

    public List<BookCopyDto> findAllBookCopies(){
        return bookCopyRepository.findAll().stream().map(bookCopyMapper::toBookCopyDto).toList();
    }

    public void updateBookCopy(int idCopy, UpdateBookCopyRequestDto updateBookCopy){
        BookCopy bookCopy = bookCopyRepository.findById(idCopy)
                .orElseThrow(()-> new IllegalArgumentException("Book Copy Not Found"));
        bookCopyMapper.updateBookCopyFromDto(updateBookCopy,bookCopy);
        bookCopyRepository.save(bookCopy);
    }

    public void deleteBookCopy(int idCopy){
        BookCopy copy = bookCopyRepository.findById(idCopy)
                .orElseThrow(()-> new IllegalArgumentException("Book Copy not found"));
        copy.makeAsDeleted();
        bookCopyRepository.save(copy);
    }
}