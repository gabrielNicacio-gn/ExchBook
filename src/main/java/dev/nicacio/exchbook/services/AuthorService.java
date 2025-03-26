package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateAuthorRequestDto;
import dev.nicacio.exchbook.dtos.response.CreateAuthorResponseDto;
import dev.nicacio.exchbook.models.Author;
import dev.nicacio.exchbook.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public CreateAuthorResponseDto registerAuthor(CreateAuthorRequestDto createAuthor){
        var author = new Author();
        author.setName(createAuthor.name());
        return new CreateAuthorResponseDto(author.getIdAuthor(),author.getName());
    }
}
