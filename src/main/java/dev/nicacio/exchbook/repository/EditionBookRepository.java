package dev.nicacio.exchbook.repository;

import dev.nicacio.exchbook.models.EditionBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditionBookRepository extends JpaRepository<EditionBook,Integer> { }
