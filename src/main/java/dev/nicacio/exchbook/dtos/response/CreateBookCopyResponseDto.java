package dev.nicacio.exchbook.dtos.response;

import dev.nicacio.exchbook.enums.Condition;

public record CreateBookCopyResponseDto(int IdCopy, String title, Condition condition) { }
