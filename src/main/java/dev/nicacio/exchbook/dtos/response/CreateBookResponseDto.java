package dev.nicacio.exchbook.dtos.response;

import dev.nicacio.exchbook.models.Author;

import java.util.List;

public record CreateBookResponseDto(int idBook, String title, List<String> nameAuthors) {
}
