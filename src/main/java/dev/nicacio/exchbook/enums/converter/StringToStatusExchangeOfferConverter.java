package dev.nicacio.exchbook.enums.converter;

import dev.nicacio.exchbook.enums.StatusExchangeOffer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
@Component
public class StringToStatusExchangeOfferConverter implements Converter<String, StatusExchangeOffer> {
    @Override
    public StatusExchangeOffer convert(String source) {
        return StatusExchangeOffer.valueOf(source.trim().toUpperCase());
    }
}