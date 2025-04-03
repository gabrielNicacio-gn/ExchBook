package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateBookEditionRequestDto;
import dev.nicacio.exchbook.dtos.response.CreateBookEditionResponseDto;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.BookEdition;
import dev.nicacio.exchbook.repository.BookEditionRepository;
import dev.nicacio.exchbook.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookEditionService {
    private final BookEditionRepository editionBookRepository;
    private final BookRepository bookRepository;

    public CreateBookEditionResponseDto registerEdition(CreateBookEditionRequestDto createEdition){
        Optional<Book> optionalBook = bookRepository.findById(createEdition.idBook());
        if(optionalBook.isEmpty()){
            throw new IllegalArgumentException("No Book found, can't create a edition");
        }
        Book book = optionalBook.get();
        BookEdition edition = new BookEdition();
        edition.setNumberEdition(createEdition.numberEdition());
        edition.setBook(book);
        edition.setYearOfPublication(createEdition.yearOfPublication());
        edition.setFormat(createEdition.format());
        BookEdition savedEdition = editionBookRepository.save(edition);

        return new CreateBookEditionResponseDto(savedEdition.getIdEditionBook(),savedEdition.getBook().getTitle(),
                savedEdition.getYearOfPublication(),savedEdition.getNumberEdition(),savedEdition.getFormat());
    }
}
