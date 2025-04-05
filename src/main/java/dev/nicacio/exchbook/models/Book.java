package dev.nicacio.exchbook.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "book")
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_book")
    private int idBook;

    @Column(name = "title")
    private String title;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "id_book"),
            inverseJoinColumns = @JoinColumn(name = "id_author")
    )
    private List<Author> authors = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<BookEdition> editions = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<BookCopy> copies = new ArrayList<>();

    @OneToMany(mappedBy = "bookDesired")
    private List<ExchangeOffer> offers = new ArrayList<>();

    public void addAuthors(List<Author> authors){
        this.authors.addAll(authors);
    }
    public void addCopies(BookCopy copyBook){
        copies.add(copyBook);
        copyBook.setBook(this);
    }
}
