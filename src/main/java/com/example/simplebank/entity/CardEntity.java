package com.example.simplebank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "card_info")
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number")
    private String cardNum;
    private String cvc;
    @Column(name = "expiry_date")
    private String expDate;
}
