package dev.nicacio.exchbook.repository;

import dev.nicacio.exchbook.models.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository extends JpaRepository<Exchange,Integer> {
}
