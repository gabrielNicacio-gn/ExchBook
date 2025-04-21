package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateBookRequestDto;
import dev.nicacio.exchbook.dtos.response.BookDto;
import dev.nicacio.exchbook.mapper.BookMapper;
import dev.nicacio.exchbook.models.Author;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.repository.AuthorRepository;
import dev.nicacio.exchbook.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

        public int registerBook(CreateBookRequestDto createBookDto){
            Book book = bookMapper.toBook(createBookDto,authorRepository);
            Book savedBook = bookRepository.save(book);
            return savedBook.getIdBook();
        }

        public BookDto getBookById(int idBook) throws ChangeSetPersister.NotFoundException {
            Book book = bookRepository.findById(idBook)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);
            return bookMapper.toBookDto(book);
        }
    }
