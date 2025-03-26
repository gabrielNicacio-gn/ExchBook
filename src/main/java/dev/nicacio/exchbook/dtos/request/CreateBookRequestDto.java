package dev.nicacio.exchbook.dtos.request;

import java.util.List;

public record CreateBookRequestDto(String title,List<Integer> authorIds ) { }
