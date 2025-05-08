package dev.nicacio.exchbook.dtos.response;

import dev.nicacio.exchbook.enums.StatusExchangeOffer;

import java.util.Date;

public record ExchangeOfferDto (int idExchangeOffer, BookCopyDto copyOffered,
                                BookDto bookDesired, Date dateOfOffer, StatusExchangeOffer statusExchangeOffer){ }
