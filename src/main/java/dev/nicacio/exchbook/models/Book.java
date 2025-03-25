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
@Table(name = "book")
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_book")
    @Setter(AccessLevel.NONE)
    private int idBook;

    @Column(name = "title")
    private String title;

    private String author;

    private String edition;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "id_book"),
            inverseJoinColumns = @JoinColumn(name = "id_author")
    )
    private List<Author> authors = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    List<EditionBook> editions = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<CopyBook> copies = new ArrayList<>();

    public void addEditions(EditionBook edition){
        editions.add(edition);
        edition.setBook(this);
    }
    public void addAuthors(Author author){
        authors.add(author);
    }
    public void addCopies(CopyBook copyBook){
        copies.add(copyBook);
        copyBook.setBook(this);
    }
}
