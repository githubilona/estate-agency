package com.example.estateagency.models;

import javax.validation.constraints.Min;
import java.io.Serializable;

public class PropertyType implements Serializable{

	@Min(1)
	private int id;

	private String name;

	public PropertyType(){}

	public PropertyType(int id, String name){
		this.id = id;
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
