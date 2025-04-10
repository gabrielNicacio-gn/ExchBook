package dev.nicacio.exchbook.dtos.response;

import dev.nicacio.exchbook.enums.StatusExchange;

import java.util.Date;

public record ExchangeOfferDto (int idExchangeOffer, BookCopyDto copy,
                                BookDto book, Date dateOfOffer, StatusExchange statusExchange){ }
