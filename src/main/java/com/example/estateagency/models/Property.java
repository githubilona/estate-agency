package com.example.estateagency.models;


import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "properties")
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Date getAvailableDate() {
		return availableDate;
	}

	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}

	public boolean isExclusive() {
		return exclusive;
	}

	public void setExclusive(boolean exclusive) {
		this.exclusive = exclusive;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
