package com.example.estateagency.controllers;

import com.example.estateagency.models.*;
import com.example.estateagency.services.AddressService;
import com.example.estateagency.services.PropertyService;
import com.example.estateagency.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
@SessionAttributes(names={"propertyTypes", "property"})
@Log4j2
public class PropertyFormController {

	private PropertyService propertyService;
	private UserService userService;
	private AddressService addressService;

	@Autowired
	ServletContext servletContext;


	//Wstrzyknięcie zależności przez konstruktor. Od wersji 4.3 Springa nie trzeba używać adnontacji @Autowired, gdy mamy jeden konstruktor
	//@Autowired
	public PropertyFormController(PropertyService propertyService, UserService userService, AddressService addressService) {
		this.propertyService = propertyService;
		this.userService=userService;
		this.addressService=addressService;
	}

	@Secured("ROLE_ADMIN")
	@GetMapping(path="/propertyForm.html")
	public String showForm(Model model, Optional<Long> id){

		model.addAttribute("property",
				id.isPresent()?
						propertyService.getProperty(id.get()):
						new Property());

		return "propertyForm";
	}


	@ModelAttribute("propertyTypes")
	public List<PropertyType> loadTypes(){
		List<PropertyType> types = propertyService.getAllTypes();
		log.info("Ładowanie listy "+types.size()+" typów ");
		return types;
	}
	@ModelAttribute("offerTypes")
	public List<OfferType> loadOfferTypes(){
		List<OfferType> types = propertyService.getAllOfferTypes();
		log.info("Ładowanie listy "+types.size()+" typów ofert ");
		return types;
	}
	@ModelAttribute("provinces")
	public List<Province> loadProvinces(){
		List<Province> types = addressService.getAllProvinces();
		log.info("Ładowanie listy "+types.size()+" typów ofert ");
		return types;
	}


//	@RequestMapping(value="/propertyForm.html", method=RequestMethod.POST)
//	public String processForm(@Valid @ModelAttribute("property") Property p, BindingResult errors,
//							  @RequestParam("file") MultipartFile file) throws IOException {
//
//		if(!file.isEmpty()) {
//
//			String uploadsDir = "/uploads/";
//			String realPathToUploads = servletContext.getRealPath(uploadsDir);
//
//			if (!new File(realPathToUploads).exists()) {
//				new File(realPathToUploads).mkdir();
//			}
//
//			String orgName = file.getOriginalFilename();
//			String filePath = realPathToUploads + orgName;
//			File dest = new File(filePath);
//			file.transferTo(dest);
//
//			p.setImageName(uploadsDir + file.getOriginalFilename());
//		}
//		if(p.getUser() == null){
//			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//			String userName = authentication.getName();
//			p.setUser(userService.getUserByUsername(userName));
//		}
//		if(errors.hasErrors()){
//			return "propertyForm";
//		}
//		log.info("Data utworzenia komponentu "+p.getCreationDate());
//		log.info("Data edycji komponentu "+new Date());
//
//		propertyService.saveProperty(p);
//
//		return "redirect:propertyList.html";//po udanym dodaniu/edycji przekierowujemy na listę
//	}


	@RequestMapping(value= "/saveProperty", method=RequestMethod.POST)
	public String processForm(@Valid @ModelAttribute("property") Property p, BindingResult errors,
							   @RequestParam(value = "files") MultipartFile[] files, ModelMap modelMap) throws IOException {

		List<String> photos = new ArrayList<String>();
		for (MultipartFile file : files) {
			String fileName = saveImage(file);
			photos.add(fileName);
		}
		p.setPhotos(photos);
		modelMap.put("property", p);

		if(p.getUser() == null){
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();
			p.setUser(userService.getUserByUsername(userName));
		}
		if(errors.hasErrors()){
			return "propertyForm";
		}
		log.info("Data utworzenia komponentu "+p.getCreationDate());
		log.info("Data edycji komponentu "+new Date());

		addressService.saveAddress(p.getAddress());
		propertyService.saveProperty(p);

		return "redirect:propertyList.html";
	}

	private String saveImage(MultipartFile multipartFile) {
		try {
			byte[] bytes = multipartFile.getBytes();

			String uploadsDir = "/uploads/";
			String realPathToUploads = servletContext.getRealPath(uploadsDir);

			String orgName = multipartFile.getOriginalFilename();
			String filePathString = realPathToUploads + orgName;
			Path path = Paths.get(filePathString);
			Files.write(path, bytes);
			return multipartFile.getOriginalFilename();
		} catch (IOException e) {
			return null;
		}
	}
    @InitBinder
    public void initBinder(WebDataBinder binder) {//Rejestrujemy edytory właściwości
    	
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
		CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, false);
        binder.registerCustomEditor(Date.class, "availableDate", dateEditor);

		DecimalFormat numberFormat = new DecimalFormat("#0.00");
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setGroupingUsed(false);
        binder.registerCustomEditor(Float.class, "price", new CustomNumberEditor(Float.class, numberFormat, false));

		binder.setDisallowedFields("creationDate");//ze względu na bezpieczeństwo aplikacji to pole nie może zostać przesłane w formularzu
    }
}








