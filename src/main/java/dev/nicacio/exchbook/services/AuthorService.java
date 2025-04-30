package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateAuthorRequestDto;
import dev.nicacio.exchbook.dtos.request.UpdateAuthorRequestDto;
import dev.nicacio.exchbook.dtos.response.AuthorDto;
import dev.nicacio.exchbook.exceptions.ResourceNotFoundException;
import dev.nicacio.exchbook.mapper.AuthorMapper;
import dev.nicacio.exchbook.models.Author;
import dev.nicacio.exchbook.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
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

    public AuthorDto findAuthorById(int idAuthor) throws ResourceNotFoundException {
        Author author = authorRepository.findById(idAuthor)
                .orElseThrow(()-> new ResourceNotFoundException("Author not found"));
        return authorMapper.toAuthorDto(author);
    }

    public List<AuthorDto> findAllAuthors(){
        return authorRepository.findAll().stream().map(authorMapper::toAuthorDto).toList();
    }

    public void updateAuthor(int idAuthor, UpdateAuthorRequestDto updateAuthor){
        Author author = authorRepository.findById(idAuthor)
                .orElseThrow(()-> new ResourceNotFoundException("Author not found"));
        author.setName(updateAuthor.name());
        authorRepository.save(author);
    }
}
