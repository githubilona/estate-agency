package com.example.estateagency.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "premium_offer")
public class PremiumOffer implements Serializable {

    @Min(0)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    @Positive
    @Max(10000)
    private float price;

    @Positive
    private int days;

    public PremiumOffer(String name, String description, @Positive @Max(10000) float price, @Positive int days) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.days = days;
    }
}
