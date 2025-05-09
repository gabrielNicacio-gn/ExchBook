package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateBookRequestDto;
import dev.nicacio.exchbook.dtos.request.UpdateBookRequestDto;
import dev.nicacio.exchbook.dtos.response.BookDto;
import dev.nicacio.exchbook.exceptions.ResourceNotFoundException;
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
            List<Author> authors = authorRepository.findAllById(createBookDto.authorIds());

            if(authors.isEmpty()){
                throw new IllegalArgumentException("Author(s) not found, can't create a book");
            }

            Book book = bookMapper.toBook(createBookDto,authors);
            Book savedBook = bookRepository.save(book);
            return savedBook.getIdBook();
        }

        public BookDto findBookById(int idBook) throws ResourceNotFoundException {
            Book book = bookRepository.findById(idBook)
                    .orElseThrow(()-> new ResourceNotFoundException("Book not found"));
            return bookMapper.toBookDto(book);
        }

        public List<BookDto> findAllBooks(){
            return bookRepository.findAll().stream().map(bookMapper::toBookDto).toList();
        }

        public void updateBook(int idBook, UpdateBookRequestDto updateBook){
            Book book = bookRepository.findById(idBook)
                    .orElseThrow(()-> new IllegalArgumentException("Book not found"));
            bookMapper.updateBookFromDto(updateBook,book);
            bookRepository.save(book);
        }

        public void deleteBook(int idBook){
            Book book = bookRepository.findById(idBook)
                    .orElseThrow(()-> new IllegalArgumentException("Book not found"));
            book.makeAsDeleted();
            bookRepository.save(book);
        }
    }
