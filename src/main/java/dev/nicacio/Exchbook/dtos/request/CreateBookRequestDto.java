package dev.nicacio.Exchbook.dtos.request;

import dev.nicacio.Exchbook.enums.Condition;

public record CreateBookRequestDto (String titleBook, String authorName, String yearOfPublication,
                                    String numberEdition, String format,Condition condition){}
