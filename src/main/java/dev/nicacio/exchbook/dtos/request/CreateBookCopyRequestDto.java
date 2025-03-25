package dev.nicacio.exchbook.dtos.request;

import dev.nicacio.exchbook.enums.Condition;

public record CreateBookCopyRequestDto(Condition condition, int IdBook){}
