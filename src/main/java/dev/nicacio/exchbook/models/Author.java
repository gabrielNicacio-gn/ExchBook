package dev.nicacio.exchbook.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DialectOverride;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "author")
public class Author {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_author")
    private int idAuthor;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "authors")
    @Setter(AccessLevel.NONE)
    private List<Book> books = new ArrayList<>();

    @Column(name = "is_deleted",nullable = false)
    @Setter(AccessLevel.PRIVATE)
    private boolean isDeleted = false;

    public void makeAsDeleted(){
        isDeleted = true;
    }

}
