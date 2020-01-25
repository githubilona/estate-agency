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
	@ManyToOne(fetch = FetchType.EAGER)//EAGER powoduje pobranie obiektu VehicleType wraz z obiektem Vehicle.
	@JoinColumn(name="type_id", nullable = false)
	private PropertyType  propertyType;

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


	public Property() {
		this.creationDate = new Date();
		this.propertyType = new PropertyType();
		this.imageName="/images/no-image-property.png";
	}

	public Property(String name, String description, float price, Date availableDate, boolean exclusive,
					PropertyType propertyType, Date creationDate, User user) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.availableDate = availableDate;
		this.exclusive = exclusive;
		this.propertyType=propertyType;
		this.creationDate=creationDate;
		this.user=user;
	}

	public void setPhotos(List<String> photos) {
		this.photos = photos;
		if(photos.get(0)==null){
			this.imageName="/images/no-image-property.png";
		}else{
			this.imageName="/uploads/" +photos.get(0);
		}

	}
}
