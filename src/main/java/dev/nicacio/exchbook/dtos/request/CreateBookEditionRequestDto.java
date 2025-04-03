package dev.nicacio.exchbook.dtos.request;

public record CreateBookEditionRequestDto(String yearOfPublication, String numberEdition, String format, int idBook) { }
