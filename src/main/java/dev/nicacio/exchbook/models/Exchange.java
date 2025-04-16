package dev.nicacio.exchbook.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "exchange")
public class Exchange {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exchange")
    private int idExchange;

    @OneToOne
    @JoinColumn(name = "id_exchange_offer")
    private ExchangeOffer exchangeOffer;

    @Column(name = "is_approved")
    private boolean is_approved;

    @Column(name = "date_of_exchange")
    @Setter(AccessLevel.NONE)
    private Date dateOfExchange;

    public Exchange(){
        is_approved = true;
        dateOfExchange = Calendar.getInstance().getTime();
    }
}
