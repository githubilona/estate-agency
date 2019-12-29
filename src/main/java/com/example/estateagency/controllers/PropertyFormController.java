package com.example.estateagency.controllers;

import com.example.estateagency.models.Property;
import com.example.estateagency.models.PropertyType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@SessionAttributes(names={"propertyTypes", "property"})
public class PropertyFormController {

	protected final Log log = LogFactory.getLog(getClass());//Dodatkowy komponent do logowania

	@GetMapping(path="/propertyForm.html")
	public String showForm(Model model, @RequestParam(name="id", required = false, defaultValue = "-1") int id){
		Property p;

		if(id>0){
			p = PropertiesListController.propertiesList.stream().filter(x->x.getId() == id).findFirst().get();
			//obsłużyć not found exception
		}else{
			p = new Property();
		}
		
		model.addAttribute("property", p);
		return "propertyForm";
	}


	@ModelAttribute("propertyTypes")
	public List<PropertyType> loadTypes(){
		List<PropertyType> types = PropertiesListController.propertyTypes;
		log.info("Ładowanie listy "+types.size()+" typów ");
		return types;
	}

	@RequestMapping(value="/propertyForm.html", method=RequestMethod.POST)
	public String processForm(@Valid @ModelAttribute("property") Property p, BindingResult errors){

		if(errors.hasErrors()){
			return "propertyForm";
		}

		log.info("Data utworzenia komponentu "+p.getCreationDate());
		log.info("Data edycji komponentu "+new Date());

		if(p.getPropertyType().getId() > 0 ) {//uzupełnianie obiektu property o typ o zadanym id.
			PropertyType selectedPropertyType =
					PropertiesListController.propertyTypes.stream().filter(x -> x.getId() == p.getPropertyType().getId()).findFirst().get();
			p.setPropertyType(selectedPropertyType);
		}

		if(p.getId() > 0){//to edycja
			for(int i =0, n = PropertiesListController.propertiesList.size(); i < n; i++){
				if(PropertiesListController.propertiesList.get(i).getId() == p.getId()){
					PropertiesListController.propertiesList.set(i, p);
					break;
				}
			}
		}else{//to dodawanie nowego
			PropertiesListController.propertiesList.add(p);
		}

		return "redirect:propertyList.html";//po udanym dodaniu/edycji przekierowujemy na listę
	}
    @InitBinder
    public void initBinder(WebDataBinder binder) {//Rejestrujemy edytory właściwości
    	
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, "availableDate", new CustomDateEditor(dateFormat, false));

		DecimalFormat numberFormat = new DecimalFormat("#0.00");
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setGroupingUsed(false);
        binder.registerCustomEditor(Float.class, "price", new CustomNumberEditor(Float.class, numberFormat, false));

    }


}








