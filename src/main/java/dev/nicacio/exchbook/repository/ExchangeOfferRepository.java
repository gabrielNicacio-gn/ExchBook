package dev.nicacio.exchbook.repository;

import dev.nicacio.exchbook.models.ExchangeOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeOfferRepository extends JpaRepository<ExchangeOffer, Integer> {
}
