package com.example.estateagency.models;


import java.util.Date;

public class Property {

	private long id;	
	private String name;
	private String propertyType;
	private float price;
	private Date availableDate;
	private boolean exclusive;

	public Property(){}

	public Property(long id, String name, String propertyType, float price, Date availableDate, boolean exclusive) {
		this.id = id;
		this.name = name;
		this.propertyType = propertyType;
		this.price = price;
		this.availableDate = availableDate;
		this.exclusive = exclusive;
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

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
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
}
