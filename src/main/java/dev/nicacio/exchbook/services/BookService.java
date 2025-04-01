package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateBookRequestDto;
import dev.nicacio.exchbook.dtos.response.CreateBookResponseDto;
import dev.nicacio.exchbook.models.Author;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.EditionBook;
import dev.nicacio.exchbook.repository.AuthorRepository;
import dev.nicacio.exchbook.repository.BookRepository;
import dev.nicacio.exchbook.repository.EditionBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final EditionBookRepository editionBookRepository;
    private final AuthorRepository authorRepository;

    public CreateBookResponseDto registerBook(CreateBookRequestDto createBookDto){
        List<Author> authors = authorRepository.findAllById(createBookDto.authorIds());

        if(authors.isEmpty()){
            throw new IllegalArgumentException("No Author found, can't create a book");
        }

        Book book = new Book();
        book.setTitle(createBookDto.title());
        book.addAuthors(authors);

        return new CreateBookResponseDto(book.getIdBook(),book.getTitle());
    }
}
