package dev.nicacio.exchbook.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateExchangeRequestDto(
        @NotNull()
        @NotEmpty(message = "At least one Id must be passed")
        int idExchangeOffer) { }
