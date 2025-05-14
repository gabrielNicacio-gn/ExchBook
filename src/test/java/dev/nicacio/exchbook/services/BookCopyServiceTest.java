package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateBookCopyRequestDto;
import dev.nicacio.exchbook.dtos.request.CreateBookRequestDto;
import dev.nicacio.exchbook.dtos.request.UpdateBookCopyRequestDto;
import dev.nicacio.exchbook.dtos.response.AuthorDto;
import dev.nicacio.exchbook.dtos.response.BookCopyDto;
import dev.nicacio.exchbook.dtos.response.BookDto;
import dev.nicacio.exchbook.enums.Condition;
import dev.nicacio.exchbook.exceptions.ResourceNotFoundException;
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
import java.util.Collections;
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
    public void shouldNotRegisterAnBookCopyAndThrowIllegalArgumentException(){
        CreateBookCopyRequestDto create = new CreateBookCopyRequestDto(Condition.NOVO,99);

        when(copyBookRepository.findById(99)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()->
                bookCopyService.registerBookCopy(create));

        assertEquals("Book not found, can't create a copy",ex.getMessage());
    }

    @Test
    public void shouldGetBookCopyById() throws ChangeSetPersister.NotFoundException {
        Book book = new Book();
        book.setIdBook(1);
        book.setTitle("MyBook");

        BookDto expectedBookDto = new BookDto(book.getIdBook(),book.getTitle(),new ArrayList<AuthorDto>());

        BookCopy bookCopy = new BookCopy();
        bookCopy.setIdCopy(1);
        bookCopy.setCondition(Condition.NOVO);
        bookCopy.setBook(book);

        BookCopyDto expectedBookCopyDto = new BookCopyDto(bookCopy.getIdCopy(),bookCopy.getCondition(),expectedBookDto);

        when(copyBookRepository.findByIdAndIsDeletedFalse(1)).thenReturn(Optional.of(bookCopy));
        when(bookCopyMapper.toBookCopyDto(bookCopy)).thenReturn(expectedBookCopyDto);

        BookCopyDto bookCopyDto = bookCopyService.findBookCopyById(1);

        verify(copyBookRepository,times(1)).findByIdAndIsDeletedFalse(1);

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

        BookCopyDto bookCopyADto = new BookCopyDto(copyA.getIdCopy(),copyA.getCondition(),new BookDto(1,null,null));
        BookCopyDto bookCopyBDto = new BookCopyDto(copyB.getIdCopy(),copyB.getCondition(),new BookDto(2,null,null));
        BookCopyDto bookCopyCDto = new BookCopyDto(copyC.getIdCopy(),copyC.getCondition(),new BookDto(3,null,null));

        List<BookCopyDto> expectedResult = List.of(bookCopyADto,bookCopyBDto,bookCopyCDto);

        when(copyBookRepository.findAllByIsDeletedFalse()).thenReturn(copyList);
        when(bookCopyMapper.toBookCopyDto(copyA)).thenReturn(bookCopyADto);
        when(bookCopyMapper.toBookCopyDto(copyB)).thenReturn(bookCopyBDto);
        when(bookCopyMapper.toBookCopyDto(copyC)).thenReturn(bookCopyCDto);

        List<BookCopyDto> result = bookCopyService.findAllBookCopies();

        verify(copyBookRepository,times(1)).findAllByIsDeletedFalse();
        assertEquals(expectedResult,result);
    }

    @Test
    public void shouldUpdateAnBookCopy(){
        UpdateBookCopyRequestDto update = new UpdateBookCopyRequestDto(Condition.CONSERVADO);

        BookCopy bookCopy = new BookCopy();
        bookCopy.setIdCopy(1);
        bookCopy.setCondition(Condition.NOVO);

        when(copyBookRepository.findByIdAndIsDeletedFalse(1)).thenReturn(Optional.of(bookCopy));
        when(copyBookRepository.save(any(BookCopy.class))).thenReturn(bookCopy);

        bookCopyService.updateBookCopy(1,update);

        verify(copyBookRepository,times(1)).findByIdAndIsDeletedFalse(1);
        verify(copyBookRepository,times(1)).save(any());
        verify(bookCopyMapper,times(1)).updateBookCopyFromDto(update,bookCopy);
    }

    @Test
    public void shouldDeletedAnBookCopy(){
        int idBookCopy = 1;

        BookCopy bookCopy = new BookCopy();
        bookCopy.setIdCopy(1);
        bookCopy.setCondition(Condition.NOVO);

        when(copyBookRepository.findByIdAndIsDeletedFalse(idBookCopy)).thenReturn(Optional.of(bookCopy));
        when(copyBookRepository.save(any(BookCopy.class))).thenReturn(bookCopy);

        bookCopyService.deleteBookCopy(idBookCopy);

        verify(copyBookRepository,times(1)).findByIdAndIsDeletedFalse(idBookCopy);
        verify(copyBookRepository,times(1)).save(any());

        assertTrue(bookCopy.isDeleted());
    }
}