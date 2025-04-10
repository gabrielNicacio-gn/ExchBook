package dev.nicacio.exchbook.dtos.response;

import java.util.List;

public record BookDto(int idBook, String title, List<AuthorDto> authors) {
}
