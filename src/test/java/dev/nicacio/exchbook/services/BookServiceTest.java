package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateBookRequestDto;
import dev.nicacio.exchbook.dtos.request.UpdateBookRequestDto;
import dev.nicacio.exchbook.dtos.response.AuthorDto;
import dev.nicacio.exchbook.dtos.response.BookDto;
import dev.nicacio.exchbook.exceptions.ResourceNotFoundException;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookMapper bookMapper;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldRegisterAnBook(){
        List<Integer> authorsIds = List.of(1);
        CreateBookRequestDto create = new CreateBookRequestDto("BookOne",authorsIds);

        Author author = new Author();
        author.setName("AuthorOne");
        author.setIdAuthor(1);
        List<Author> authorsList = List.of(author);

        Book expectedBook = new Book();
        expectedBook.setIdBook(1);
        expectedBook.setTitle("BookOne");
        expectedBook.addAuthors(authorsList);

        when(bookMapper.toBook(any(CreateBookRequestDto.class),any())).thenReturn(expectedBook);
        when(bookRepository.save(any(Book.class))).thenReturn(expectedBook);
        when(authorRepository.findAllById(List.of(1))).thenReturn(authorsList);

        int idCreatedBook = bookService.registerBook(create);

        verify(authorRepository,times(1)).findAllById(authorsIds);
        verify(bookRepository,times(1)).save(any());

        assertEquals("BookOne",expectedBook.getTitle());
        assertEquals(expectedBook.getIdBook(),idCreatedBook);
    }

    @Test
    public void shouldNotRegisterAnBookAndThrowIllegalArgumentException(){
        List<Integer> authorsIds = List.of(99);
        CreateBookRequestDto create = new CreateBookRequestDto("BookOne",authorsIds);

        when(bookRepository.findAllById(List.of(99))).thenReturn(Collections.emptyList());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()->
                bookService.registerBook(create));

        assertEquals("Author(s) not found, can't create a book",ex.getMessage());
    }

    @Test
    public void shouldGetBookById() throws ChangeSetPersister.NotFoundException {
        List<Integer> authorsIds = List.of(1);
        Author author = new Author();
        author.setIdAuthor(1);
        author.setName("First Author");

        List<Author> authors = List.of(author);

        AuthorDto authorDto = new AuthorDto(author.getIdAuthor(),author.getName());
        List<AuthorDto> authorDtos = List.of(authorDto);

        Book book = new Book();
        book.setIdBook(1);
        book.setTitle("My Book");
        book.addAuthors(authors);

        BookDto expectedBookDto = new BookDto(book.getIdBook(),book.getTitle(),authorDtos);

        when(bookRepository.findByIdAndIsDeletedFalse(1)).thenReturn(Optional.of(book));
        when(bookMapper.toBookDto(book)).thenReturn(expectedBookDto);

        BookDto bookDto = bookService.findBookById(1);

        verify(bookRepository,times(1)).findByIdAndIsDeletedFalse(1);

        assertEquals(expectedBookDto.idBook(),bookDto.idBook());
        assertEquals(expectedBookDto.title(),bookDto.title());
        assertEquals(expectedBookDto.authors(),bookDto.authors());
    }

    @Test
    public void shouldGetAllBooks(){
        Book bookA = new Book();
        bookA.setIdBook(1);
        bookA.setTitle("BookA");
        bookA.addAuthors(new ArrayList<>());

        Book bookB = new Book();
        bookB.setIdBook(2);
        bookB.setTitle("BookB");
        bookB.addAuthors(new ArrayList<>());

        Book bookC = new Book();
        bookC.setIdBook(1);
        bookC.setTitle("BookC");
        bookC.addAuthors(new ArrayList<>());
        List<Book> bookList = List.of(bookA,bookB,bookC);

        BookDto bookADto = new BookDto(bookA.getIdBook(),bookA.getTitle(),new ArrayList<>());
        BookDto bookBDto = new BookDto(bookB.getIdBook(),bookB.getTitle(),new ArrayList<>());
        BookDto bookCDto = new BookDto(bookC.getIdBook(),bookC.getTitle(),new ArrayList<>());
        List<BookDto> expectedResult = List.of(bookADto,bookBDto,bookCDto);

        when(bookRepository.findAllByIsDeletedFalse()).thenReturn(bookList);
        when(bookMapper.toBookDto(bookA)).thenReturn(bookADto);
        when(bookMapper.toBookDto(bookB)).thenReturn(bookBDto);
        when(bookMapper.toBookDto(bookC)).thenReturn(bookCDto);

        List<BookDto> result = bookService.findAllBooks();

        verify(bookRepository,times(1)).findAllByIsDeletedFalse();
        assertEquals(expectedResult,result);
    }

    @Test
    public void shouldUpdateAnBook(){
        int idBook = 1;
        UpdateBookRequestDto requestDto = new UpdateBookRequestDto("New Title");

        Book savedBook = new Book();
        savedBook.setIdBook(1);
        savedBook.setTitle("Current Title");

        when(bookRepository.findByIdAndIsDeletedFalse(1)).thenReturn(Optional.of(savedBook));
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        bookService.updateBook(idBook,requestDto);

        verify(bookMapper,times(1)).updateBookFromDto(requestDto,savedBook);
        verify(bookRepository,times(1)).findByIdAndIsDeletedFalse(idBook);
        verify(bookRepository,times(1)).save(any(Book.class));

    }

    @Test
    public void shouldDeletedAnBook(){
        int idBook = 1;

        Book savedBook = new Book();
        savedBook.setIdBook(1);
        savedBook.setTitle("Current Title");

        when(bookRepository.findByIdAndIsDeletedFalse(idBook)).thenReturn(Optional.of(savedBook));
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        bookService.deleteBook(idBook);

        verify(bookRepository,times(1)).findByIdAndIsDeletedFalse(idBook);
        verify(bookRepository,times(1)).save(any(Book.class));

        assertTrue(savedBook.isDeleted());

    }
}