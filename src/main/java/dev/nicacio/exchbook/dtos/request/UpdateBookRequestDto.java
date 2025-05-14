package dev.nicacio.exchbook.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateBookRequestDto(
        @NotBlank(message = "Title is required")
        String title) { }
