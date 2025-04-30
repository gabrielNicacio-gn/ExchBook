package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateBookCopyRequestDto;
import dev.nicacio.exchbook.dtos.response.BookCopyDto;
import dev.nicacio.exchbook.mapper.BookCopyMapper;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.BookCopy;
import dev.nicacio.exchbook.repository.BookRepository;
import dev.nicacio.exchbook.repository.BookCopyRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookCopyService {

    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final BookCopyMapper bookCopyMapper;

    public int registerBookCopy(CreateBookCopyRequestDto createBook) {
        BookCopy newCopy = bookCopyMapper.toBookCopy(createBook, bookRepository);
        BookCopy savedCopy = bookCopyRepository.save(newCopy);
        return savedCopy.getIdCopy();
    }
    public BookCopyDto findBookCopyById(int idBookCopy) throws ChangeSetPersister.NotFoundException {
        BookCopy bookCopy = bookCopyRepository.findById(idBookCopy)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        return bookCopyMapper.toBookCopyDto(bookCopy);
    }

    public List<BookCopyDto> findAllBookCopies(){
        return bookCopyRepository.findAll().stream().map(bookCopyMapper::toBookCopyDto).toList();
    }
}