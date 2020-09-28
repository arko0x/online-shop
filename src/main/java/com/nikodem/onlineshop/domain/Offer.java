package com.nikodem.onlineshop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

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

    @NotNull
    @ManyToOne
    private Brand brand;

    @NotNull
    @NotBlank
    private String carModel;

    @Column(columnDefinition="TEXT")
    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotBlank
    @Size(max = 30)
    private String whereItIs;

    @NotBlank
    @Size(max = 100)
    private String whyForSale;

    private Boolean isForNegotiation;

    private int price;

    private Date placedAt;

    @ManyToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offer")
    private List<OfferImage> images;


    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand=" + brand +
                ", carModel='" + carModel + '\'' +
                ", description='" + description + '\'' +
                ", whereItIs='" + whereItIs + '\'' +
                ", whyForSale='" + whyForSale + '\'' +
                ", isForNegotiation=" + isForNegotiation +
                ", price=" + price +
                ", placedAt=" + placedAt +
                ", user=" + user +
                '}';
    }

    @Override
    public int compareTo(Offer o) {
        return this.placedAt.compareTo(o.getPlacedAt());
    }
}
