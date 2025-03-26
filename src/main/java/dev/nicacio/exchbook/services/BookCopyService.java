package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateBookCopyRequestDto;
import dev.nicacio.exchbook.dtos.response.CreateBookCopyResponseDto;
import dev.nicacio.exchbook.models.CopyBook;
import dev.nicacio.exchbook.repository.BookRepository;
import dev.nicacio.exchbook.repository.CopyBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookCopyService {

    private final BookRepository bookRepository;
    private final CopyBookRepository copyBookRepository;
    public CreateBookCopyResponseDto registerBookCopy(CreateBookCopyRequestDto createBook){

        var book = bookRepository.findAll().get(createBook.IdBook());

        var newCopy = new CopyBook();
        newCopy.setBook(book);
        newCopy.setCondition(createBook.condition());
        copyBookRepository.save(newCopy);

        return new CreateBookCopyResponseDto(newCopy.getIdCopy(),book.getTitle(),newCopy.getCondition());
    }
}
