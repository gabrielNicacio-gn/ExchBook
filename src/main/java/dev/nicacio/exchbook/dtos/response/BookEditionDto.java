package dev.nicacio.exchbook.dtos.response;

import dev.nicacio.exchbook.models.Book;

public record BookEditionDto(int idEdition, String yearOfPublication, String numberEdition, String format, Book book) { }
