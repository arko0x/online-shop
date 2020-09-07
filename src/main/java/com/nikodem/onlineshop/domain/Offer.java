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
public class Offer implements Comparable<Offer>{
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

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", whereItIs='" + whereItIs + '\'' +
                ", whyForSale='" + whyForSale + '\'' +
                ", isForNegotiation=" + isForNegotiation +
                ", price=" + price +
                ", placedAt=" + placedAt +
                '}';
    }

    @Override
    public int compareTo(Offer o) {
        return this.placedAt.compareTo(o.getPlacedAt());
    }
}
