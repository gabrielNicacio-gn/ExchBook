package dev.nicacio.exchbook.mapper;

import dev.nicacio.exchbook.dtos.request.CreateAuthorRequestDto;
import dev.nicacio.exchbook.dtos.request.UpdateAuthorRequestDto;
import dev.nicacio.exchbook.dtos.response.AuthorDto;
import dev.nicacio.exchbook.models.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toAuthor(CreateAuthorRequestDto requestDto);
    AuthorDto toAuthorDto(Author author);
    void updateAuthorFromDto(UpdateAuthorRequestDto update, @MappingTarget Author author);
}
