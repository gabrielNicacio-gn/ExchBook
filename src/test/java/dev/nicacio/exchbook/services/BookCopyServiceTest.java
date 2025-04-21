package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateBookCopyRequestDto;
import dev.nicacio.exchbook.dtos.response.AuthorDto;
import dev.nicacio.exchbook.dtos.response.BookCopyDto;
import dev.nicacio.exchbook.dtos.response.BookDto;
import dev.nicacio.exchbook.enums.Condition;
import dev.nicacio.exchbook.mapper.BookCopyMapper;
import dev.nicacio.exchbook.models.Author;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.BookCopy;
import dev.nicacio.exchbook.repository.BookCopyRepository;
import dev.nicacio.exchbook.repository.BookRepository;
import org.checkerframework.checker.units.qual.C;
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

class BookCopyServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookCopyRepository copyBookRepository;
    private final BookCopyMapper bookCopyMapper = Mappers.getMapper(BookCopyMapper.class);
    @InjectMocks
    private BookCopyService bookCopyService;
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        bookCopyService = new BookCopyService(bookRepository,copyBookRepository,bookCopyMapper);
    }
    @Test
    public void shouldRegisterAnBookCopy(){
        CreateBookCopyRequestDto create = new CreateBookCopyRequestDto(Condition.NOVO,1);
        Optional<Book> book = Optional.of(new Book());
        book.get().setIdBook(1);
        book.get().setTitle("New Book");

        BookCopy bookCopy = new BookCopy();
        bookCopy.setIdCopy(1);
        bookCopy.setCondition(Condition.NOVO);
        bookCopy.setBook(book.get());

        when(bookRepository.findById(any())).thenReturn(book);
        when(copyBookRepository.save(any(BookCopy.class))).thenReturn(bookCopy);

        int idCreatedBookCopy = bookCopyService.registerBookCopy(create);

        verify(bookRepository,times(1)).findById(1);
        verify(copyBookRepository,times(1)).save(any());

        assertEquals(bookCopy.getIdCopy(),idCreatedBookCopy);
    }

    @Test
    public void shouldGetBookCopyById() throws ChangeSetPersister.NotFoundException {
        Book book = new Book();
        book.setIdBook(1);
        book.setTitle("MyBook");

        BookDto expectedBookDto = new BookDto(book.getIdBook(),book.getTitle(),new ArrayList<AuthorDto>());

        Optional<BookCopy> bookCopy = Optional.of( new BookCopy());
        bookCopy.get().setIdCopy(1);
        bookCopy.get().setCondition(Condition.NOVO);
        bookCopy.get().setBook(book);

        BookCopyDto expectedBookCopyDto = new BookCopyDto(bookCopy.get().getIdCopy(),bookCopy.get().getCondition(),expectedBookDto);

        when(copyBookRepository.findById(1)).thenReturn(bookCopy);

        BookCopyDto bookCopyDto = bookCopyService.getBookCopyById(1);

        verify(copyBookRepository,times(1)).findById(1);

        assertEquals(expectedBookCopyDto.idCopy(),bookCopyDto.idCopy());
        assertEquals(expectedBookCopyDto.condition(),bookCopyDto.condition());
        assertEquals(expectedBookCopyDto.book(),bookCopyDto.book());

    }
}