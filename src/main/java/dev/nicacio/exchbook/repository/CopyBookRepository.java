package dev.nicacio.exchbook.repository;

import dev.nicacio.exchbook.models.CopyBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CopyBookRepository extends JpaRepository<CopyBook,Integer> { }
