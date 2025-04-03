package dev.nicacio.exchbook.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "book_edition")
public class BookEdition {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_book_edition")
    @Setter(AccessLevel.NONE)
    private int IdEditionBook;

    @Column(name = "year_of_publication")
    private String yearOfPublication;

    @Column(name = "number_edition")
    private String numberEdition;

    @Column(name = "format")
    private String format;

    @ManyToOne
    @JoinColumn(name = "id_book")
    private Book book;

}
