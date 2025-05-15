package dev.nicacio.exchbook.repository;

import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExchangeRepository extends JpaRepository<Exchange,Integer> {
    @Query("SELECT e FROM Exchange e WHERE e.idExchange = :idExchange AND isDeleted = false")
    Optional<Exchange> findByIdAndIsDeletedFalse(@Param("idExchange") int idExchange);
    @Query("SELECT e FROM Exchange e WHERE isDeleted = false")
    List<Exchange> findAllByIsDeletedFalse();
}
