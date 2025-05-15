package dev.nicacio.exchbook.repository;

import dev.nicacio.exchbook.models.BookCopy;
import dev.nicacio.exchbook.models.BookEdition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookEditionRepository extends JpaRepository<BookEdition,Integer> {
    @Query("SELECT be FROM BookEdition be WHERE be.IdEditionBook = :idEdition AND isDeleted = false")
    Optional<BookEdition> findByIdAndIsDeletedFalse(@Param("IdEditionBook") int IdEditionBook);

    @Query("SELECT be FROM BookEdition be WHERE isDeleted = false")
    List<BookEdition> findAllByIsDeletedFalse();
}
