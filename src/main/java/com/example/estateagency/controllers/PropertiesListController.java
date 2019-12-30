package com.example.estateagency.controllers;

import com.example.estateagency.controllers.commands.PropertyFilter;
import com.example.estateagency.models.Property;
import com.example.estateagency.repositories.PropertyRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.text.DecimalFormat;

@Controller
@SessionAttributes("searchCommand")
public class PropertiesListController {

	protected final Log log = LogFactory.getLog(getClass());//Dodatkowy komponent do logowania
	
	@Autowired
	PropertyRepository propertyRepository;

	@Secured("IS_AUTHENTICATED_FULLY")
	@GetMapping(value="/propertyList.html", params = "id")
	public String showPropertyDetails(Model model, long id){
		System.out.println("Pokayzwanie szczegółów");

		Property p = propertyRepository.findById(id).get();
		//obłużyć not found exception
		model.addAttribute("property", p);
		return "propertyDetails";
	}
	
	@ModelAttribute("searchCommand")
	public PropertyFilter getSimpleSearch(){
		return new PropertyFilter();
	}

	@GetMapping(value="/propertyList.html", params = {"all"})
	public String resetehicleList(@ModelAttribute("searchCommand") PropertyFilter search){
		search.clear();
		return "redirect:propertyList.html";
	}

	@RequestMapping(value="/propertyList.html", method = {RequestMethod.GET, RequestMethod.POST})
	public String showPropertyList(Model model, Pageable pageable, @Valid @ModelAttribute("searchCommand") PropertyFilter search, BindingResult result){
		Page page;
		if(search.isEmpty()){
			page = propertyRepository.findAll(pageable);
		}else{
			page = propertyRepository.findAllPropertiesUsingFilter(search.getPhraseLIKE(), search.getMinPrice(), search.getMaxPrice(), pageable);
		}
		
		model.addAttribute("propertyListPage", page);
		return "propertyList";
	}
	@Secured("ROLE_ADMIN")
	@GetMapping(path="/propertyList.html", params={"did"})
	public String deleteProperty(long did, HttpServletRequest request){
		log.info("Usuwanie ogłoszenia o id "+did);
		propertyRepository.deleteById(did);
		String queryString = prepareQueryString(request.getQueryString());
		return String.format("redirect:propertyList.html?%s", queryString);//robimy przekierowanie, ale zachowując parametry pageingu

	}
	private String prepareQueryString(String queryString) {//np., did=20&page=2&size=20
		return queryString.substring(queryString.indexOf("&")+1);//obcinamy parametr did, bo inaczej znowu będzie wywołana metoda deleteProperty
	}
	@InitBinder
	public void initBinder(WebDataBinder binder) {//Rejestrujemy edytory właściwości
		DecimalFormat numberFormat = new DecimalFormat("#0.00");
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setGroupingUsed(false);
		CustomNumberEditor priceEditor = new CustomNumberEditor(Float.class, numberFormat, true);
		binder.registerCustomEditor(Float.class, "minPrice", priceEditor);
		binder.registerCustomEditor(Float.class, "maxPrice", priceEditor);
	}

}
