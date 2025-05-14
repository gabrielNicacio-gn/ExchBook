package dev.nicacio.exchbook.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateEditionRequestDto (
        @NotBlank(message = "Year of publication is required")
        @Pattern(regexp = "\\d{4}", message = "Year of publication must be a 4-digit number")
        String yearOfPublication,
        @NotBlank(message = "Number edition is required")
        @Pattern(regexp = "\\d+", message = "Number edition must be numeric")
        String numberEdition,
        @NotBlank(message = "Format is required")
        String format ) { }
