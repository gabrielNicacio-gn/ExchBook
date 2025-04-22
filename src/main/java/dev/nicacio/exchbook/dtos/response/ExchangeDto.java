package dev.nicacio.exchbook.dtos.response;

import java.util.Date;

public record ExchangeDto(int idExchange, Date dateOfExchange, ExchangeOfferDto exchangeOffer) {
}
