package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateBookEditionRequestDto;
import dev.nicacio.exchbook.dtos.response.BookDto;
import dev.nicacio.exchbook.dtos.response.BookEditionDto;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookEditionServiceTest {
    @Mock
    private BookEditionRepository editionBookRepository;
    @Mock
    private BookRepository bookRepository;
    private final BookEditionMapper bookEditionMapper = Mappers.getMapper(BookEditionMapper.class);
    @InjectMocks
    private BookEditionService bookEditionService;
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        bookEditionService = new BookEditionService(editionBookRepository,bookRepository,bookEditionMapper);
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

        when(bookRepository.findById(1)).thenReturn(book);
        when(editionBookRepository.save(any(BookEdition.class))).thenReturn(bookEdition);

        int idCreatedEditionBook = bookEditionService.registerEdition(create);

        verify(bookRepository,times(1)).findById(1);
        verify(editionBookRepository,times(1)).save(any(BookEdition.class));

        assertEquals(bookEdition.getIdEditionBook(),idCreatedEditionBook);

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

        BookEditionDto bookEditionDto = bookEditionService.getBookEditionById(1);

        verify(editionBookRepository,times(1)).findById(1);

        assertEquals(expectedBookEditionDto,bookEditionDto);
    }

}