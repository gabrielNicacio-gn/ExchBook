package dev.nicacio.exchbook.repository;

import dev.nicacio.exchbook.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Integer> {
    Author findByName(String name);
}
