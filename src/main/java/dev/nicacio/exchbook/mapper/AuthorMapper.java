package dev.nicacio.exchbook.mapper;

import dev.nicacio.exchbook.dtos.request.CreateAuthorRequestDto;
import dev.nicacio.exchbook.models.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toAuthor(CreateAuthorRequestDto requestDto);
}
