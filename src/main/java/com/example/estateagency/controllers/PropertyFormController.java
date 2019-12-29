package com.example.estateagency.controllers;

import com.example.estateagency.models.Property;
import com.example.estateagency.models.PropertyType;
import com.example.estateagency.repositories.PropertyRepository;
import com.example.estateagency.repositories.PropertyTypeRepository;
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

	private PropertyRepository propertyRepository;
	private PropertyTypeRepository propertyTypeRepository;

	//Wstrzyknięcie zależności przez konstruktor. Od wersji 4.3 Springa nie trzeba używać adnontacji @Autowired, gdy mamy jeden konstruktor
	//@Autowired
	public PropertyFormController(
			PropertyRepository vehicleRepository,
			PropertyTypeRepository vehicleTypeRepository
	)
	{
		this.propertyRepository = vehicleRepository;
		this.propertyTypeRepository = vehicleTypeRepository;
	}

	@GetMapping(path="/propertyForm.html")
	public String showForm(Model model, @RequestParam(name="id", required = false, defaultValue = "-1") long id){
		Property p;

		if(id>0){
			p = propertyRepository.findById(id).get();
			//obsłużyć not found exception
		}else{
			p = new Property();
		}
		
		model.addAttribute("property", p);
		return "propertyForm";
	}


	@ModelAttribute("propertyTypes")
	public List<PropertyType> loadTypes(){
		List<PropertyType> types = propertyTypeRepository.findAll();
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

		propertyRepository.save(p);

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








