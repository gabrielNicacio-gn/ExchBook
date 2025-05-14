package dev.nicacio.exchbook.dtos.request;

import dev.nicacio.exchbook.enums.Condition;
import jakarta.validation.constraints.NotBlank;

public record UpdateBookCopyRequestDto(
        @NotBlank(message = "Condition is Required")
        Condition condition) { }
