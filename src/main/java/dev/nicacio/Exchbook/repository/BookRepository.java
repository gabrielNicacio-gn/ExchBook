package dev.nicacio.Exchbook.repository;

import dev.nicacio.Exchbook.models.Book;
import dev.nicacio.Exchbook.models.CopyBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findByTitleAndAuthorAndEdition(String title, String author,String edition);
}


