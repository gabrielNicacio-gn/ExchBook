package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateBookCopyRequestDto;
import dev.nicacio.exchbook.dtos.response.BookCopyDto;
import dev.nicacio.exchbook.mapper.BookCopyMapper;
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
    private final BookCopyMapper bookCopyMapper;

    public int registerBookCopy(CreateBookCopyRequestDto createBook) {
        BookCopy newCopy = bookCopyMapper.toBookCopy(createBook, bookRepository);
        BookCopy savedCopy = copyBookRepository.save(newCopy);
        return savedCopy.getIdCopy();
    }
}