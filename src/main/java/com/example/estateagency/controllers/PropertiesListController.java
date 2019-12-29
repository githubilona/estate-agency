package com.example.estateagency.controllers;

import com.example.estateagency.models.Property;
import com.example.estateagency.models.PropertyType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PropertiesListController {

	public static List<Property> propertiesList = new ArrayList<Property>();
	public static List<PropertyType> propertyTypes = new ArrayList<>();
	static{

		propertyTypes.add(new PropertyType(1, "Dom"));
		propertyTypes.add(new PropertyType(2, "Mieszkanie"));
		propertyTypes.add(new PropertyType(3, "Pokój"));
		propertyTypes.add(new PropertyType(4, "Inne"));


		int id = 1;
		Property p1 = new Property();
		p1.setId(id++);
		p1.setName("An Exceptionally High Quality House in Prestigious Highgate Area");
		p1.setDescription("\n" +
				"Witam, mam do sprzedania piękne, nowoczesne, w pełni wyremontowane 3-pokojowe mieszkanie z balkonem o powierzchni 53 m² na osiedlu Polskich Skrzydeł. Mieszkanie znajduje się na I piętrze 4 piętrowego bloku, klatka czysta i zadbana, pod blokiem znajduje się prywatny parking z licznymi miejscami postojowymi z bramą zamykaną na pilota.\n" );
		p1.setAvailableDate(new Date(2020,6,1));
		p1.setPrice(469000);
		p1.setExclusive(true);
		p1.setPropertyType(propertyTypes.get(0));
		p1.setCreationDate(new Date());

		propertiesList.add(p1);

		Property p2 = new Property();
		p2.setId(id++);
		p2.setName("2 Double Bed Flat In The Heart of Crouch End");
		p2.setAvailableDate(new Date(2020,3,21));
		p2.setPrice(1850);
		p2.setPropertyType(propertyTypes.get(0));
		p2.setCreationDate(new Date());

		propertiesList.add(p2);

		Property p3 = new Property();
		p3.setId(id++);
		p3.setName("DOUBLE BEDROOM AVAILABLE FOR ONE MONTH");
		p3.setDescription("Sprzedam mieszkanie 3-pokojowe z ogródkiem przy ul. Toruńskiej.\n" +
				"Nowo powstałe osiedle.\n");
		p3.setAvailableDate(new Date(2020,8,16));
		p3.setPrice(1740);
		p3.setPropertyType(propertyTypes.get(2));
		p3.setCreationDate(new Date());

		propertiesList.add(p3);

		Property p4 = new Property();
		p4.setId(id++);
		p4.setName("3 Bed House, Brampton Road, Bexleyheath");
		p4.setDescription("Wśród zalet oferowanych mieszkań oprócz atrakcyjnej, nowoczesnej architektury na szczególną uwagę zasługują znakomicie doświetlone pokoje dzięki zastosowaniu panoramicznych okien oraz przestronne, częściowo przeszklone tarasy i balkony");
		p1.setExclusive(true);
		p4.setAvailableDate(new Date(2020,3,26));
		p4.setPrice(1900000);
		p4.setPropertyType(propertyTypes.get(3));
		p4.setCreationDate(new Date());

		propertiesList.add(p4);
	}

	@Secured("IS_AUTHENTICATED_FULLY")
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
	@Secured("ROLE_ADMIN")
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
