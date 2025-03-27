package dev.nicacio.exchbook.repository;

import dev.nicacio.exchbook.models.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CopyBookRepository extends JpaRepository<BookCopy,Integer> { }
