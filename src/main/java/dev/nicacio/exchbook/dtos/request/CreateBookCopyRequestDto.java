package dev.nicacio.exchbook.dtos.request;

import dev.nicacio.exchbook.enums.Condition;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateBookCopyRequestDto(
        @NotBlank(message = "Condition is Required")
        Condition condition,

        @NotEmpty(message = "At least one Id must be passed")
        @NotNull()
        int idBook){ }
