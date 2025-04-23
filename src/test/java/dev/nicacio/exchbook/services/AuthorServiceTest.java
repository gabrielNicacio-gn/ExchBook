package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateAuthorRequestDto;
import dev.nicacio.exchbook.dtos.response.AuthorDto;
import dev.nicacio.exchbook.mapper.AuthorMapper;
import dev.nicacio.exchbook.models.Author;
import dev.nicacio.exchbook.repository.AuthorRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;
    private final AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);
    @InjectMocks
    private AuthorService authorService;
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        authorService = new AuthorService(authorRepository,authorMapper);
    }
    @Test
    public void shouldRegisterAnAuthor(){
        CreateAuthorRequestDto create = new CreateAuthorRequestDto("NameAuthorOne");

        Author savedAuthor = new Author();
        savedAuthor.setIdAuthor(1);
        savedAuthor.setName("NameAuthorOne");

        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);

        int idCreatedAuthor = authorService.registerAuthor(create);

        verify(authorRepository,times(1)).save(any());
        assertEquals("NameAuthorOne",savedAuthor.getName());
        assertEquals(savedAuthor.getIdAuthor(),idCreatedAuthor);
    }

    @Test
    public void shouldGetAuthorById() throws ChangeSetPersister.NotFoundException {
        int idAuthor = 2;

        Optional<Author> author = Optional.of(new Author());
        author.get().setIdAuthor(2);
        author.get().setName("First Author");

        AuthorDto authorDto = new AuthorDto(author.get().getIdAuthor(),author.get().getName());

        when(authorRepository.findById(idAuthor)).thenReturn(author);

        AuthorDto authorResponse = authorService.getAuthorById(2);

        verify(authorRepository,times(1)).findById(any());
        assertEquals(authorDto.idAuthor(),authorResponse.idAuthor());
        assertEquals(authorDto.name(),authorResponse.name());

    }

    @Test
    public void shouldGetAllAuthors(){
        Author authorA = new Author();
        authorA.setIdAuthor(1);
        authorA.setName("AuthorA");

        Author authorB = new Author();
        authorB.setIdAuthor(2);
        authorB.setName("AuthorB");

        Author authorC = new Author();
        authorC.setIdAuthor(3);
        authorC.setName("AuthorC");

        List<Author> authorList = List.of(authorA,authorB,authorC);

        AuthorDto authorADto = new AuthorDto(authorA.getIdAuthor(),authorA.getName());
        AuthorDto authorBDto = new AuthorDto(authorB.getIdAuthor(),authorB.getName());
        AuthorDto authorCDto = new AuthorDto(authorC.getIdAuthor(),authorC.getName());

        List<AuthorDto> expectedList = List.of(authorADto,authorBDto,authorCDto);

        when(authorRepository.findAll()).thenReturn(authorList);

        List<AuthorDto> result = authorService.getAllAuthors();

        verify(authorRepository,times(1)).findAll();

        assertEquals(expectedList,result);
    }
}