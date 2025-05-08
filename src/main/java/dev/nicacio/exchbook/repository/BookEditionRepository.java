package dev.nicacio.exchbook.repository;

import dev.nicacio.exchbook.models.BookEdition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookEditionRepository extends JpaRepository<BookEdition,Integer> { }
