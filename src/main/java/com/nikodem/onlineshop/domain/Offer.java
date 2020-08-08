package com.nikodem.onlineshop.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    private String where;

    private String whyForSale;

    private Boolean isForNegotation;

    private double price;

    private Date placedAt;



    @ManyToOne
    private User user;
}
