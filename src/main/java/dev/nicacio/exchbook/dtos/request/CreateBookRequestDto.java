package dev.nicacio.exchbook.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateBookRequestDto(
        @NotBlank(message = "Title is required")
        String title,

        @NotEmpty(message ="At least one Id must be passed")
        @NotNull(message = "Author Id cannot be null")
        List<Integer> authorIds ) { }
