package com.nikodem.onlineshop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Size(max = 100)
    @NotBlank
    private String name;

    @Column(columnDefinition="TEXT")
    @NotBlank
    @Size(max = 400)
    private String description;

    @NotBlank
    @Size(max = 30)
    private String whereItIs;

    @NotBlank
    @Size(max = 100)
    private String whyForSale;

    private Boolean isForNegotiation;

    private double price;

    private Date placedAt;

    @ManyToOne
    private User user;
}
