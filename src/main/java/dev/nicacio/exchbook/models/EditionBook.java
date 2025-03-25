package dev.nicacio.exchbook.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "edition_book")
public class EditionBook {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_edition_book")
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
