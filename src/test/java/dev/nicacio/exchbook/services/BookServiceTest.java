package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateBookRequestDto;
import dev.nicacio.exchbook.mapper.BookMapper;
import dev.nicacio.exchbook.models.Author;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.repository.AuthorRepository;
import dev.nicacio.exchbook.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository,authorRepository,bookMapper);
    }

    @Test
    public void shouldRegisterAnBook(){
        List<Integer> authorsIds = List.of(1);
        CreateBookRequestDto create = new CreateBookRequestDto("BookOne",authorsIds);
        Optional<Author> author = Optional.of(new Author());
        author.get().setName("AuthorOne");
        author.get().setIdAuthor(1);

        Book savedBook = new Book();
        savedBook.setIdBook(2);
        savedBook.setTitle("BookOne");
        savedBook.addAuthors(List.of(author.get()));

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
        when(authorRepository.findAllById(List.of(1))).thenReturn(List.of(author.get()));

        int idBook = bookService.registerBook(create);

        verify(authorRepository,times(1)).findAllById(authorsIds);
        verify(bookRepository,times(1)).save(any());

        assertEquals("BookOne",savedBook.getTitle());
    }
  
}