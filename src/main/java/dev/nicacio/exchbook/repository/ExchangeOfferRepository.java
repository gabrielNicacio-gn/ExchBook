package dev.nicacio.exchbook.repository;

import dev.nicacio.exchbook.enums.StatusExchangeOffer;
import dev.nicacio.exchbook.models.ExchangeOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExchangeOfferRepository extends JpaRepository<ExchangeOffer, Integer> {

        @Query("SELECT eo FROM ExchangeOffer eo WHERE eo.statusExchangeOffer = :statusExchangeOffer AND isDeleted = false")
        List<ExchangeOffer> findByStatusExchangeOffer(@Param("statusExchangeOffer") StatusExchangeOffer statusExchangeOffer);
        @Query("SELECT eo FROM ExchangeOffer eo WHERE isDeleted = false")
        List<ExchangeOffer> findAllByIsDeletedFalse();
        @Query("SELECT eo FROM ExchangeOffer eo WHERE eo.idExchangeOffer = :idExchangeOffer AND isDeleted = false")
        Optional<ExchangeOffer> findByIdAndIsDeletedFalse(@Param("idExchangeOffer") int idExchangeOffer);
}
