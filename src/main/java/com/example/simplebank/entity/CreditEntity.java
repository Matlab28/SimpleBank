package com.example.simplebank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "credit_info")
public class CreditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean working;
    @Column(name = "your_need")
    private Integer yourNeed;
    private Integer salary, months;
}
