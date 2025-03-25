package dev.nicacio.exchbook.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user_owner")
public class UserOwner {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    @Setter(AccessLevel.NONE)
    private int IdUser;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    //@OneToMany
    //@JoinColumn(name = "id_user")
    //private List<CopyBook> copyBooks;
}
