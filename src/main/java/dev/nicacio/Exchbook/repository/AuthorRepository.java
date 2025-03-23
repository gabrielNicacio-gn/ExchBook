package dev.nicacio.Exchbook.repository;

import dev.nicacio.Exchbook.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Integer> {
    Author findByName(String name);
}
