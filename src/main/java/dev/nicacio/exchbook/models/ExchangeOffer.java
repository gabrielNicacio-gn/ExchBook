package dev.nicacio.exchbook.models;

import dev.nicacio.exchbook.enums.StatusExchangeOffer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name="exchange_offer")
public class ExchangeOffer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_exchange_offer")
    private int idExchangeOffer;

    @ManyToOne
    @JoinColumn(name = "id_copy_offered")
    private BookCopy copyOffered;

    @ManyToOne
    @JoinColumn(name = "id_book_desired")
    private Book bookDesired;

    @Column(name = "status_exchange")
    @Enumerated(EnumType.STRING)
    private StatusExchangeOffer statusExchangeOffer;

    @Column(name ="date_of_offer")
    private Date dateOfOffer;

    @OneToOne(mappedBy = "exchangeOffer")
    @Setter(AccessLevel.NONE)
    private Exchange exchange;

    public ExchangeOffer(){
        statusExchangeOffer = StatusExchangeOffer.OPEN;
        dateOfOffer = Calendar.getInstance().getTime();
    }
}
