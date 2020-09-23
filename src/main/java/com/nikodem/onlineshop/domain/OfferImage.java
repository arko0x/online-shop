package com.nikodem.onlineshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class OfferImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String URL;

    @ManyToOne
    private Offer offer;

    public OfferImage(String filename, String URL) {
        this.name = filename;
        this.URL = URL;
    }
}
