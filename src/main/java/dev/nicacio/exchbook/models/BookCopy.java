package dev.nicacio.exchbook.models;

import dev.nicacio.exchbook.enums.Condition;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "book_copy")
public class BookCopy {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_book_copy")
    @Setter(AccessLevel.NONE)
    private int idCopy;

    @Column(name = "condition")
    private Condition condition;

    @ManyToOne()
    @JoinColumn(name = "id_book")
    private Book book;

    @OneToMany(mappedBy = "copyOffered")
    @Setter(AccessLevel.NONE)
    private List<ExchangeOffer> offers = new ArrayList<>();

    //@ManyToOne
    //@JoinColumn(name = "id_user_owner")
    //private UserOwner user;
}
