package dev.nicacio.exchbook.repository;

import dev.nicacio.exchbook.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author,Integer> {
    @Query("SELECT a FROM Author a WHERE a.idAuthor = :idAuthor AND isDeleted = false")
    Optional<Author> findByIdAndIsDeletedFalse(@Param("idAuthor") int idAuthor);

    @Query("SELECT a FROM Author a WHERE isDeleted = false")
    List<Author> findAllByIsDeletedFalse();
    Author findByName(String name);
}
