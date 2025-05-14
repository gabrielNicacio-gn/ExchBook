package dev.nicacio.exchbook.dtos.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateAuthorRequestDto(
        @NotBlank(message = "Name is required")
        String name
) { }
