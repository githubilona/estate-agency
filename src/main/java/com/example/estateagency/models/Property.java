package com.example.estateagency.models;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "properties")
@NamedQuery(name = "Property.findAllPropertiesUsingNamedQuery",
        query = "SELECT p FROM Property p WHERE upper(p.name) LIKE upper(:phrase) or upper(p.description) LIKE upper(:phrase) or upper(p.propertyType.name) LIKE upper(:phrase)")
public class Property {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	//@Size(min = 2, max = 30)
	@Length(min = 2, max = 1000)
	private String name;

	@NotBlank
	@Size(min = 2, max = 100000)
	private String description;

	@Positive
	@Max(1000000000)
	private float price;

	@Column(name="available_date")
    @Temporal(TemporalType.DATE)
	private Date availableDate;

	private boolean exclusive;

	@Valid
	@ManyToOne(fetch = FetchType.EAGER)//EAGER powoduje pobranie obiektu VehicleType wraz z obiektem Vehicle.
	@JoinColumn(name="type_id", nullable = false)
	private PropertyType  propertyType;

	@Column(name="created_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	public Property() {
		this.creationDate = new Date();
		this.propertyType = new PropertyType();
	}

	public Property(String name, String description, float price, Date availableDate, boolean exclusive, PropertyType propertyType, Date creationDate) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.availableDate = availableDate;
		this.exclusive = exclusive;
		this.propertyType=propertyType;
		this.creationDate=creationDate;
	}

}
