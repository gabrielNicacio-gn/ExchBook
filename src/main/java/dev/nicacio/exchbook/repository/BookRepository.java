package dev.nicacio.exchbook.repository;

import dev.nicacio.exchbook.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findByTitleAndAuthorAndEdition(String title, String author,String edition);
}


