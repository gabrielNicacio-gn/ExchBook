package dev.nicacio.exchbook.dtos.response;

import dev.nicacio.exchbook.enums.StatusExchange;

import java.util.Date;

public record ExchangeOfferDto (int idExchangeOffer, BookCopyDto copyOffered,
                                BookDto bookDesired, Date dateOfOffer, StatusExchange statusExchange){ }
