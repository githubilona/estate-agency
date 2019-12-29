package com.example.estateagency.controllers;

import com.example.estateagency.models.Property;
import com.example.estateagency.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;

@Controller
public class PropertiesListController {

	@Autowired
	private PropertyRepository propertyRepository;

	@Secured("IS_AUTHENTICATED_FULLY")
	@GetMapping(value="/propertyList.html", params = "id")
	public String showPropertyDetails(Model model, long id){
		System.out.println("Pokayzwanie szczegółów");

		Property p = propertyRepository.findById(id).get();
		//obłużyć not found exception
		model.addAttribute("property", p);
		return "propertyDetails";
	}

	@GetMapping(value= "/propertyList.html")
	@Transactional
	public String showPropertyList(Model model){
		model.addAttribute("propertyList", propertyRepository.findAll());
		return "propertyList";
	}
	@Secured("ROLE_ADMIN")
	@GetMapping(path="/propertyList.html", params={"did"})
	public String deleteVehicle(long did){
		propertyRepository.deleteById(did);
		return "redirect:propertyList.html";
	}

}
