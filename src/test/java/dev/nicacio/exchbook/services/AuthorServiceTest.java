package dev.nicacio.exchbook.services;

import dev.nicacio.exchbook.dtos.request.CreateAuthorRequestDto;
import dev.nicacio.exchbook.mapper.AuthorMapper;
import dev.nicacio.exchbook.models.Author;
import dev.nicacio.exchbook.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private AuthorMapper authorMapper;
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

        when(authorMapper.toAuthor(create)).thenReturn(savedAuthor);
        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);

        int idCreatedAuthor = authorService.registerAuthor(create);

        verify(authorRepository,times(1)).save(any());
        assertEquals("NameAuthorOne",savedAuthor.getName());
        assertEquals(savedAuthor.getIdAuthor(),idCreatedAuthor);
    }
}