package dev.nicacio.exchbook.dtos.response;

import dev.nicacio.exchbook.enums.Condition;
import dev.nicacio.exchbook.models.Book;

public record BookCopyDto(int idCopy, Condition condition, BookDto book){ }
