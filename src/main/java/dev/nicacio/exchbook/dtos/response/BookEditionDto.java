package dev.nicacio.exchbook.dtos.response;

import dev.nicacio.exchbook.models.Book;

public record BookEditionDto(int idEditionBook, String yearOfPublication, String numberEdition, String format, BookDto book) { }
