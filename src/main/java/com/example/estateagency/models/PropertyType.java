package com.example.estateagency.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
@Table(name = "property_types")
public class PropertyType implements Serializable{

	@Min(0)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	public PropertyType(){}

	public PropertyType(String name){
		this.name = name;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
