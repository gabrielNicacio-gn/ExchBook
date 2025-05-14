package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateBookCopyRequestDto;
import dev.nicacio.exchbook.dtos.request.CreateBookEditionRequestDto;
import dev.nicacio.exchbook.dtos.request.UpdateEditionRequestDto;
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
    public void shouldNotRegisterAnBookCopyAndThrowIllegalArgumentException(){
        CreateBookEditionRequestDto create = new CreateBookEditionRequestDto("2025","2",
                "Hardcover",99);

        when(editionBookRepository.findById(99)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()->
                bookEditionService.registerEdition(create));

        assertEquals("Book not found, can't create a edition",ex.getMessage());
    }

    @Test
    public void shouldGetBookEditionById() throws ChangeSetPersister.NotFoundException {
        Book book = new Book();
        book.setIdBook(1);
        book.setTitle("MyBook");
        book.addAuthors(new ArrayList<>());

        BookDto bookDto = new BookDto(book.getIdBook(),book.getTitle(),new ArrayList<>());

        BookEdition bookEdition = new BookEdition();
        bookEdition.setIdEditionBook(1);
        bookEdition.setNumberEdition("2");
        bookEdition.setFormat("Hardcover");
        bookEdition.setYearOfPublication("2025");
        bookEdition.setBook(book);

        BookEditionDto expectedBookEditionDto = new BookEditionDto(bookEdition.getIdEditionBook(),bookEdition.getYearOfPublication()
                ,bookEdition.getNumberEdition(),bookEdition.getFormat(),bookDto);

        when(editionBookRepository.findByIdAndIsDeletedFalse(1)).thenReturn(Optional.of(bookEdition));
        when(bookEditionMapper.toBookEditionDto(bookEdition)).thenReturn(expectedBookEditionDto);

        BookEditionDto bookEditionDto = bookEditionService.findBookEditionById(1);

        verify(editionBookRepository,times(1)).findByIdAndIsDeletedFalse(1);

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

        List<BookEdition> bookEditionList = List.of(bookEditionA,bookEditionB);

        BookEditionDto bookEditionDtoA = new BookEditionDto(bookEditionA.getIdEditionBook(),bookEditionA.getYearOfPublication()
                ,bookEditionA.getNumberEdition(),bookEditionA.getFormat(),null);
        BookEditionDto bookEditionDtoB = new BookEditionDto(bookEditionB.getIdEditionBook(),bookEditionB.getYearOfPublication()
                ,bookEditionB.getNumberEdition(),bookEditionB.getFormat(),null);

        List<BookEditionDto> expectedResult = List.of(bookEditionDtoA,bookEditionDtoB);

        when(editionBookRepository.findAllByIsDeletedFalse()).thenReturn(bookEditionList);
        when(bookEditionMapper.toBookEditionDto(bookEditionA)).thenReturn(bookEditionDtoA);
        when(bookEditionMapper.toBookEditionDto(bookEditionB)).thenReturn(bookEditionDtoB);

        List<BookEditionDto> result = bookEditionService.findAllBookEdition();

        verify(editionBookRepository,times(1)).findAllByIsDeletedFalse();
        assertEquals(expectedResult,result);
    }
    @Test
    public void shouldUpdateAnBookEdition(){
        int idEdition = 1;
        UpdateEditionRequestDto update = new UpdateEditionRequestDto("2025","2",
                "Hardcover");
        BookEdition bookEdition = new BookEdition();
        bookEdition.setIdEditionBook(1);
        bookEdition.setNumberEdition("4");
        bookEdition.setFormat("Normal Cover");
        bookEdition.setYearOfPublication("2025");

        when(editionBookRepository.findByIdAndIsDeletedFalse(1)).thenReturn(Optional.of(bookEdition));

        bookEditionService.updateEdition(idEdition,update);

        verify(editionBookRepository,times(1)).findByIdAndIsDeletedFalse(idEdition);
        verify(bookEditionMapper).updateBookEditionFromDto(update,bookEdition);
        verify(editionBookRepository,times(1)).save(any(BookEdition.class));
    }

    @Test
    public void shouldDeletedAnBookEdition(){
        int idEdition = 1;

        BookEdition bookEdition = new BookEdition();
        bookEdition.setIdEditionBook(1);
        bookEdition.setNumberEdition("4");
        bookEdition.setFormat("Normal Cover");
        bookEdition.setYearOfPublication("2025");

        when(editionBookRepository.findByIdAndIsDeletedFalse(1)).thenReturn(Optional.of(bookEdition));

        bookEditionService.deleteEdition(idEdition);

        verify(editionBookRepository,times(1)).findByIdAndIsDeletedFalse(idEdition);
        verify(editionBookRepository,times(1)).save(any(BookEdition.class));

        assertTrue(bookEdition.isDeleted());
    }

}