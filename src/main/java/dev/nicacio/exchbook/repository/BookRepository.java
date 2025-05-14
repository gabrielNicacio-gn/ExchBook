package dev.nicacio.exchbook.repository;

import dev.nicacio.exchbook.models.Author;
import dev.nicacio.exchbook.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("SELECT b FROM Book b WHERE b.idBook = :idBook AND isDeleted = false")
    Optional<Book> findByIdAndIsDeletedFalse(@Param("idBook") int idBook);

    @Query("SELECT b FROM Book b WHERE isDeleted = false")
    List<Book> findAllByIsDeletedFalse();
}


