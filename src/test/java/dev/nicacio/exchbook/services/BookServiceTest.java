package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateBookRequestDto;
import dev.nicacio.exchbook.dtos.response.AuthorDto;
import dev.nicacio.exchbook.dtos.response.BookDto;
import dev.nicacio.exchbook.mapper.BookMapper;
import dev.nicacio.exchbook.models.Author;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.repository.AuthorRepository;
import dev.nicacio.exchbook.repository.BookRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister;

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

        int idCreatedBook = bookService.registerBook(create);

        verify(authorRepository,times(2)).findAllById(authorsIds);
        verify(bookRepository,times(1)).save(any());

        assertEquals("BookOne",savedBook.getTitle());
        assertEquals(savedBook.getIdBook(),idCreatedBook);
    }
    @Test
    public void shouldGetBookById() throws ChangeSetPersister.NotFoundException {
        List<Integer> authorsIds = List.of(1,2);
        Author firstAuthor = new Author();
        firstAuthor.setIdAuthor(1);
        firstAuthor.setName("First Author");

        Author secondAuthor = new Author();
        secondAuthor.setIdAuthor(2);
        secondAuthor.setName("Second Author");
        List<Author> authors = List.of(firstAuthor,secondAuthor);

        AuthorDto firstAuthorDto = new AuthorDto(firstAuthor.getIdAuthor(),firstAuthor.getName());
        AuthorDto secondAuthorDto = new AuthorDto(secondAuthor.getIdAuthor(),secondAuthor.getName());
        List<AuthorDto> authorDtos = List.of(firstAuthorDto,secondAuthorDto);

        Optional<Book> book = Optional.of(new Book());
        book.get().setIdBook(1);
        book.get().setTitle("My Book");
        book.get().addAuthors(authors);

        BookDto expectedBookDto = new BookDto(book.get().getIdBook(),book.get().getTitle(),authorDtos);

        when(bookRepository.findById(1)).thenReturn(book);

        BookDto bookDto = bookService.getBookById(1);

        verify(bookRepository,times(1)).findById(1);

        assertEquals(expectedBookDto.idBook(),bookDto.idBook());
        assertEquals(expectedBookDto.title(),bookDto.title());
        assertEquals(expectedBookDto.authors(),bookDto.authors());
    }
}