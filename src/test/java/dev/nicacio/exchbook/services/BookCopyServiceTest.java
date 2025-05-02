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
    @Mock
    private  BookCopyMapper bookCopyMapper;
    @InjectMocks
    private BookCopyService bookCopyService;
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
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

        when(bookCopyMapper.toBookCopy(create,book.get())).thenReturn(bookCopy);
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

        BookCopyDto bookCopyDto = bookCopyService.findBookCopyById(1);

        verify(copyBookRepository,times(1)).findById(1);

        assertEquals(expectedBookCopyDto.idCopy(),bookCopyDto.idCopy());
        assertEquals(expectedBookCopyDto.condition(),bookCopyDto.condition());
        assertEquals(expectedBookCopyDto.book(),bookCopyDto.book());

    }

    @Test
    public void shouldGetBookCopies(){
        BookCopy copyA = new BookCopy();
        copyA.setIdCopy(1);
        copyA.setCondition(Condition.NOVO);
        copyA.setBook(new Book());

        BookCopy copyB = new BookCopy();
        copyA.setIdCopy(1);
        copyA.setCondition(Condition.NOVO);
        copyA.setBook(new Book());

        BookCopy copyC = new BookCopy();
        copyA.setIdCopy(1);
        copyA.setCondition(Condition.NOVO);
        copyA.setBook(new Book());

        List<BookCopy> copyList = List.of(copyA,copyB,copyC);

        BookCopyDto bookCopyADto = bookCopyMapper.toBookCopyDto(copyA);
        BookCopyDto bookCopyBDto = bookCopyMapper.toBookCopyDto(copyB);
        BookCopyDto bookCopyCDto = bookCopyMapper.toBookCopyDto(copyC);

        List<BookCopyDto> expectedResult = List.of(bookCopyADto,bookCopyBDto,bookCopyCDto);

        when(copyBookRepository.findAll()).thenReturn(copyList);

        List<BookCopyDto> result = bookCopyService.findAllBookCopies();

        verify(copyBookRepository,times(1)).findAll();
        assertEquals(expectedResult,result);
    }
}