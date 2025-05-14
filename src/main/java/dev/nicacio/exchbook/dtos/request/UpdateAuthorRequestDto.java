package dev.nicacio.exchbook.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateAuthorRequestDto(
        @NotBlank(message = "Name is required")
        String name) { }
