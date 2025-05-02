package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateBookCopyRequestDto;
import dev.nicacio.exchbook.dtos.request.CreateBookEditionRequestDto;
import dev.nicacio.exchbook.dtos.response.BookDto;
import dev.nicacio.exchbook.dtos.response.BookEditionDto;
import dev.nicacio.exchbook.enums.Condition;
import dev.nicacio.exchbook.exceptions.ResourceNotFoundException;
import dev.nicacio.exchbook.mapper.BookEditionMapper;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.BookEdition;
import dev.nicacio.exchbook.repository.BookEditionRepository;
import dev.nicacio.exchbook.repository.BookRepository;
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

class BookEditionServiceTest {
    @Mock
    private BookEditionRepository editionBookRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookEditionMapper bookEditionMapper;
    @InjectMocks
    private BookEditionService bookEditionService;
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void shouldRegisterAnBookEdition(){
        CreateBookEditionRequestDto create = new CreateBookEditionRequestDto("2025","2",
                "Hardcover",1);
        Optional<Book> book = Optional.of(new Book());
        book.get().setIdBook(1);
        book.get().setTitle("My Book");

        BookEdition bookEdition = new BookEdition();
        bookEdition.setIdEditionBook(1);
        bookEdition.setNumberEdition("2");
        bookEdition.setFormat("HardCover");
        bookEdition.setYearOfPublication("2025");

        when(bookEditionMapper.toBookEdition(create,book.get())).thenReturn(bookEdition);
        when(bookRepository.findById(1)).thenReturn(book);
        when(editionBookRepository.save(any(BookEdition.class))).thenReturn(bookEdition);

        int idCreatedEditionBook = bookEditionService.registerEdition(create);

        verify(bookRepository,times(1)).findById(1);
        verify(editionBookRepository,times(1)).save(any(BookEdition.class));

        assertEquals(bookEdition.getIdEditionBook(),idCreatedEditionBook);

    }
    @Test
    public void shouldNotRegisterAnBookCopyAndThrowResourceNotFoundException(){
        CreateBookEditionRequestDto create = new CreateBookEditionRequestDto("2025","2",
                "Hardcover",99);

        when(editionBookRepository.findById(99)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,()->
                bookEditionService.registerEdition(create));

        assertEquals("Book not found",ex.getMessage());
    }

    @Test
    public void shouldGetBookEditionById() throws ChangeSetPersister.NotFoundException {
        Book book = new Book();
        book.setIdBook(1);
        book.setTitle("MyBook");
        book.addAuthors(new ArrayList<>());

        BookDto bookDto = new BookDto(book.getIdBook(),book.getTitle(),new ArrayList<>());

        Optional<BookEdition> bookEdition = Optional.of(new BookEdition());
        bookEdition.get().setIdEditionBook(1);
        bookEdition.get().setNumberEdition("2");
        bookEdition.get().setFormat("Hardcover");
        bookEdition.get().setYearOfPublication("2025");
        bookEdition.get().setBook(book);

        BookEditionDto expectedBookEditionDto = new BookEditionDto(bookEdition.get().getIdEditionBook(),bookEdition.get().getYearOfPublication()
                ,bookEdition.get().getNumberEdition(),bookEdition.get().getFormat(),bookDto);

        when(editionBookRepository.findById(1)).thenReturn(bookEdition);

        BookEditionDto bookEditionDto = bookEditionService.findBookEditionById(1);

        verify(editionBookRepository,times(1)).findById(1);

        assertEquals(expectedBookEditionDto,bookEditionDto);
    }

    @Test
    public void shouldGetAllBookEdition(){
        BookEdition bookEditionA = new BookEdition();
        bookEditionA.setIdEditionBook(1);
        bookEditionA.setNumberEdition("4");
        bookEditionA.setFormat("Hardcover");
        bookEditionA.setYearOfPublication("2024");

        BookEdition bookEditionB = new BookEdition();
        bookEditionB.setIdEditionBook(2);
        bookEditionB.setNumberEdition("5");
        bookEditionB.setFormat("Hardcover");
        bookEditionB.setYearOfPublication("2014");

        BookEdition bookEditionC = new BookEdition();
        bookEditionC.setIdEditionBook(3);
        bookEditionC.setNumberEdition("2");
        bookEditionC.setFormat("Hardcover");
        bookEditionC.setYearOfPublication("2008");

        List<BookEdition> bookEditionList = List.of(bookEditionA,bookEditionB,bookEditionC);

        BookEditionDto bookEditionADto = bookEditionMapper.toBookEditionDto(bookEditionA);
        BookEditionDto bookEditionBDto = bookEditionMapper.toBookEditionDto(bookEditionB);
        BookEditionDto bookEditionCDto = bookEditionMapper.toBookEditionDto(bookEditionC);

        List<BookEditionDto> expectedResult = List.of(bookEditionADto,bookEditionBDto,bookEditionCDto);

        when(editionBookRepository.findAll()).thenReturn(bookEditionList);

        List<BookEditionDto> result = bookEditionService.findAllBookEdition();

        verify(editionBookRepository,times(1)).findAll();
        assertEquals(expectedResult,result);
    }

}