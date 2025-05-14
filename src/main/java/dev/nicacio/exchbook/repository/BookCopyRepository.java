package dev.nicacio.exchbook.repository;

import dev.nicacio.exchbook.models.Book;
import dev.nicacio.exchbook.models.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookCopyRepository extends JpaRepository<BookCopy,Integer> {
    @Query("SELECT bc FROM BookCopy bc WHERE bc.idCopy = :idCopy AND isDeleted = false")
    Optional<BookCopy> findByIdAndIsDeletedFalse(@Param("idCopy") int idBook);

    @Query("SELECT bc FROM BookCopy bc WHERE isDeleted = false")
    List<BookCopy> findAllByIsDeletedFalse();
}
