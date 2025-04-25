package dev.nicacio.exchbook.repository;

import dev.nicacio.exchbook.enums.StatusExchangeOffer;
import dev.nicacio.exchbook.models.ExchangeOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeOfferRepository extends JpaRepository<ExchangeOffer, Integer> {
        List<ExchangeOffer> findByStatusExchangeOffer(StatusExchangeOffer statusExchangeOffer);
}
