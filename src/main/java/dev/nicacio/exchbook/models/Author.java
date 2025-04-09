package dev.nicacio.exchbook.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "author")
public class Author {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_author")
    @Setter(AccessLevel.NONE)
    private int idAuthor;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "authors")
    @Setter(AccessLevel.NONE)
    private List<Book> books = new ArrayList<>();
}
