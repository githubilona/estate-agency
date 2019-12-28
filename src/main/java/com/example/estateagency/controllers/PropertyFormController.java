package com.example.estateagency.controllers;

import com.example.estateagency.models.Property;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
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
	
	@PostMapping(path="/propertyForm.html")
	public String processForm(Model model, final Property p){

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








