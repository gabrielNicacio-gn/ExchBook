package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateAuthorRequestDto;
import dev.nicacio.exchbook.dtos.request.UpdateAuthorRequestDto;
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
    @Mock
    private  AuthorMapper authorMapper;
    @InjectMocks
    private AuthorService authorService;
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
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
        int idAuthor = 1;

        Author author = new Author();
        author.setIdAuthor(1);
        author.setName("First Author");

        AuthorDto authorDto = new AuthorDto(author.getIdAuthor(),author.getName());

        when(authorRepository.findByIdAndIsDeletedFalse(idAuthor)).thenReturn(Optional.of(author));
        when(authorMapper.toAuthorDto(author)).thenReturn(authorDto);

        AuthorDto authorResponse = authorService.findAuthorById(idAuthor);

        verify(authorRepository,times(1)).findByIdAndIsDeletedFalse(idAuthor);
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

        List<Author> authorList = List.of(authorA,authorB);

        AuthorDto authorADto = new AuthorDto(authorA.getIdAuthor(),authorA.getName());
        AuthorDto authorBDto = new AuthorDto(authorB.getIdAuthor(),authorB.getName());

        List<AuthorDto> expectedList = List.of(authorADto,authorBDto);

        when(authorRepository.findAllByIsDeletedFalse()).thenReturn(authorList);
        when(authorMapper.toAuthorDto(authorA)).thenReturn(authorADto);
        when(authorMapper.toAuthorDto(authorB)).thenReturn(authorBDto);

        List<AuthorDto> result = authorService.findAllAuthors();

        verify(authorRepository,times(1)).findAllByIsDeletedFalse();

        assertEquals(expectedList,result);
    }

    @Test
    public void shouldUpdateAnAuthor(){
        int idAuthor = 1;
        UpdateAuthorRequestDto update = new UpdateAuthorRequestDto("NameAuthorOne");

        Author savedAuthor =new Author();
        savedAuthor.setIdAuthor(1);
        savedAuthor.setName("Cristiano Ronaldo");

        when(authorRepository.findByIdAndIsDeletedFalse(idAuthor)).thenReturn(Optional.of(savedAuthor));
        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);

        authorService.updateAuthor(idAuthor,update);

        verify(authorRepository,times(1)).findByIdAndIsDeletedFalse(idAuthor);
        verify(authorRepository,times(1)).save(any());
        verify(authorMapper,times(1)).updateAuthorFromDto(update,savedAuthor);
    }

    @Test
    public void shouldDeletedAnAuthor(){
        int idAuthor = 1;

        Author savedAuthor =new Author();
        savedAuthor.setIdAuthor(1);
        savedAuthor.setName("Cristiano Ronaldo");

        when(authorRepository.findByIdAndIsDeletedFalse(idAuthor)).thenReturn(Optional.of(savedAuthor));
        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);

        authorService.deleteAuthor(idAuthor);

        verify(authorRepository,times(1)).findByIdAndIsDeletedFalse(idAuthor);
        verify(authorRepository,times(1)).save(any());

        assertTrue(savedAuthor.isDeleted());
    }
}