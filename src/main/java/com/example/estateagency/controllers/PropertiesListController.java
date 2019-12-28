package com.example.estateagency.controllers;

import com.example.estateagency.models.Property;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PropertiesListController {

	private static List<Property> propertiesList = new ArrayList<Property>();

	static{

		int id = 1;
		Property p1 = new Property();
		p1.setId(id++);
		p1.setName("An Exceptionally High Quality House in Prestigious Highgate Area");
		p1.setPropertyType("House");
		p1.setAvailableDate(new Date(2020,6,1));
		p1.setPrice(469000);
		p1.setExclusive(true);
		propertiesList.add(p1);

		Property p2 = new Property();
		p2.setId(id++);
		p2.setName("2 Double Bed Flat In The Heart of Crouch End");
		p2.setAvailableDate(new Date(2020,3,21));
		p2.setPrice(1850);
		propertiesList.add(p2);

		Property p3 = new Property();
		p3.setId(id++);
		p3.setName("DOUBLE BEDROOM AVAILABLE FOR ONE MONTH");
		p3.setPropertyType("Flat");
		p3.setAvailableDate(new Date(2020,8,16));
		p3.setPrice(1740);
		propertiesList.add(p3);

		Property p4 = new Property();
		p4.setId(id++);
		p4.setName("3 Bed House, Brampton Road, Bexleyheath");
		p4.setPropertyType("House");
		p1.setExclusive(true);
		p4.setAvailableDate(new Date(2020,3,26));
		p4.setPrice(1900000);
		propertiesList.add(p4);
	}


	@RequestMapping(value= "/property.html", method = RequestMethod.GET)
	public String showPropertyDetails(Model model){
		System.out.println("Pokazywanie szczegółów");

		Property p = new Property();
		p.setId(1);
		p.setName(null);
		p.setPropertyType("House");
		p.setAvailableDate(new Date(2020,8,16));
		p.setPrice(174000);

		model.addAttribute("property", p);
		return "propertyDetails";
	}

	@RequestMapping(value= "/propertyList.html", method = RequestMethod.GET)
	public String showPropertyList(Model model){
		model.addAttribute("propertyList", propertiesList);
		return "propertyList";
	}

}
