package dev.nicacio.exchbook.dtos.response;

import dev.nicacio.exchbook.enums.StatusExchange;

import java.util.Date;

public record ExchangeOfferDto (int idExchangeOffer, String titleBookOffered,
                                String titleBookDesired, Date dateOfOffer, StatusExchange statusExchange){ }
