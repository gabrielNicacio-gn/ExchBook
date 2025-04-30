package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateBookEditionRequestDto;
import dev.nicacio.exchbook.dtos.response.BookEditionDto;
import dev.nicacio.exchbook.mapper.BookEditionMapper;
import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.BookEdition;
import dev.nicacio.exchbook.repository.BookEditionRepository;
import dev.nicacio.exchbook.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookEditionService {
    private final BookEditionRepository editionBookRepository;
    private final BookRepository bookRepository;
    private final BookEditionMapper bookEditionMapper;

    public int registerEdition(CreateBookEditionRequestDto createEdition){
        BookEdition edition = bookEditionMapper.toBookEdition(createEdition,bookRepository);
        BookEdition savedEdition = editionBookRepository.save(edition);
        return savedEdition.getIdEditionBook();
    }
    public BookEditionDto findBookEditionById(int idBookEdition) throws ChangeSetPersister.NotFoundException {
        BookEdition bookEdition = editionBookRepository.findById(idBookEdition)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        return bookEditionMapper.toBookEditionDto(bookEdition);
    }
    public List<BookEditionDto> findAllBookEdition(){
        return editionBookRepository.findAll().stream().map(bookEditionMapper::toBookEditionDto).toList();
    }
}
