package com.example.estateagency.controllers;

import com.example.estateagency.models.Property;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PropertiesListController {

	public static List<Property> propertiesList = new ArrayList<Property>();

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

	@GetMapping(value="/propertyList.html", params = "id")
	public String showPropertyDetails(Model model, int id){
		System.out.println("Pokayzwanie szczegółów");

		Property v = propertiesList.stream().filter(x->x.getId() == id).findFirst().get();
		//obłużyć not found exception
		model.addAttribute("property", v);
		return "propertyDetails";
	}

	@GetMapping(value= "/propertyList.html")
	public String showPropertyList(Model model){
		model.addAttribute("propertyList", propertiesList);
		return "propertyList";
	}
	@GetMapping(path="/propertyList.html", params={"did"})
	public String deleteVehicle(int did){
		for(int i =0, n = PropertiesListController.propertiesList.size(); i < n; i++){
			if(PropertiesListController.propertiesList.get(i).getId() == did){
				PropertiesListController.propertiesList.remove(i);
				break;
			}
		}
		return "redirect:propertyList.html";
	}

}
