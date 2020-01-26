package com.example.estateagency.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String city;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;

    @NotNull
    @Column(nullable = false)
    private String street;

    @NotNull
    @Column(name = "local_number", nullable = false)
    private String localNumber;

    public Address(@NotNull String city, @NotNull Province province, @NotNull String street, @NotNull String localNumber) {
        this.city = city;
        this.province = province;
        this.street = street;
        this.localNumber = localNumber;
    }
}
