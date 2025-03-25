package dev.nicacio.exchbook.models;

import dev.nicacio.exchbook.enums.Condition;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "copy_book")
public class CopyBook {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_copy_bok")
    @Setter(AccessLevel.NONE)
    private int idCopy;

    @Column(name = "condition")
    private Condition condition;

    @ManyToOne()
    @JoinColumn(name = "id_book")
    private Book book;

    //@ManyToOne
    //@JoinColumn(name = "id_user_owner")
    //private UserOwner user;
}
