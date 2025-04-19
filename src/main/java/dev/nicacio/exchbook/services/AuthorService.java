package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateAuthorRequestDto;
import dev.nicacio.exchbook.dtos.response.AuthorDto;
import dev.nicacio.exchbook.mapper.AuthorMapper;
import dev.nicacio.exchbook.models.Author;
import dev.nicacio.exchbook.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public int registerAuthor(CreateAuthorRequestDto createAuthor){
        Author author = authorMapper.toAuthor(createAuthor);
        Author savedAuthor = authorRepository.save(author);
        return savedAuthor.getIdAuthor();
    }

    public AuthorDto getAuthorById(int idAuthor) throws ChangeSetPersister.NotFoundException {
        Author author = authorRepository.findById(idAuthor)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        return authorMapper.toAuthorDto(author);
    }
}
