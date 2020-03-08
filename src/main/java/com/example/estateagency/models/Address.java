package com.example.estateagency.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    @ManyToOne
    @JoinColumn(name = "province_id", nullable = true)
    private Province province;

    private String street;

    private String localNumber;

    public Address(String city,  Province province, String street, String localNumber) {
        this.city = city;
        this.province = province;
        this.street = street;
        this.localNumber = localNumber;
    }
}
