package dev.nicacio.Exchbook.dtos.response;

import dev.nicacio.Exchbook.enums.Condition;

public record CreateBookResponseDto(int IdCopy,String title, Condition condition) { }
