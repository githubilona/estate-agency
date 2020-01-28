package com.example.estateagency.models;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

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
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="type_id", nullable = false)
	private PropertyType  propertyType;

	@Valid
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="offer_type_id", nullable = false)
	private OfferType  offerType;

	@Column(name="created_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	private String imageName;

	@JoinColumn(name="user_id", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@Column (name = "photos")
	@ElementCollection
	@CollectionTable (name = "Photos", joinColumns = @JoinColumn (name = "item_id"))
	private List<String> photos;

	@Positive
	@Max(30)
	private int numberOfRooms;

	@JoinColumn(name="address_id", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Address address;

	@Positive
	private float area;

	private boolean furnished;

	@JoinColumn(name="premium_offer_id", nullable = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private PremiumOffer premiumOffer;


	public Property() {
		this.creationDate = new Date();
		this.propertyType = new PropertyType();
		this.offerType = new OfferType();
		this.address = new Address();
		this.imageName="/images/no-image-property.png";
	}

	public Property(String name, String description, float price, Date availableDate, boolean exclusive,
					PropertyType propertyType, OfferType offerType, Date creationDate, User user, int numberOfRooms) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.availableDate = availableDate;
		this.exclusive = exclusive;
		this.propertyType=propertyType;
		this.offerType=offerType;
		this.creationDate=creationDate;
		this.user=user;
		this.numberOfRooms=numberOfRooms;
	}

	public void setPhotos(List<String> photos) {
		this.photos = photos;
		if(photos.get(0)==null){
			this.imageName="/images/no-image-property.png";
		}else{
			this.imageName="/uploads/" +photos.get(0);
		}

	}

	// Do formlarza propertyForm potrzebane (nie wkrywa lomboka)
	public Address getAddress() {
		return address;
	}
	public String getCity(){
		return getAddress().getCity();
	}
	public String getStreet(){
		return getAddress().getStreet();
	}
	public String getLocalNumber(){
		return getAddress().getLocalNumber();
	}

	public void setAddress(Address address) {
		this.address=address;
	}
	public void setCity(String city){
		getAddress().setCity(city);
	}
	public void setStreet(String street){
		getAddress().setStreet(street);
	}
	public void setLocalNumber(String localNumber){
		getAddress().setLocalNumber(localNumber);
	}

}
