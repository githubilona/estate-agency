package com.example.estateagency.controllers;

import com.example.estateagency.models.Property;
import com.example.estateagency.models.User;
import com.example.estateagency.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Date;


@Controller
public class UserRegistrationFormController {
    @Autowired(required = false)
    private UserService userService;

    @Autowired
    private ServletContext servletContext;


    @GetMapping("/registrationForm.html")
    public String registration(Model model) {
        model.addAttribute("userCommand", new User());
        return "registrationForm";
    }

    @PostMapping("/registrationForm.html")
    public String registration(@Valid @ModelAttribute("userCommand") User userForm, BindingResult bindingResult,
                               @RequestParam("file") MultipartFile file) throws IOException {

        if(!file.isEmpty()) {

            String uploadsDir = "/uploads/user-images/";
            String realPathToUploads = servletContext.getRealPath(uploadsDir);

            if (!new File(realPathToUploads).exists()) {
                new File(realPathToUploads).mkdir();
            }

            String orgName = file.getOriginalFilename();
            String filePath = realPathToUploads + orgName;
            File dest = new File(filePath);
            file.transferTo(dest);

            userForm.setImageName(uploadsDir + file.getOriginalFilename());
        }

        if (bindingResult.hasErrors()) {
            return "registrationForm";
        }
        userService.save(userForm);
        return "registrationSuccess";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //aby użytkownik nie mógł sobie wstrzyknąć aktywacji konta oraz ról (np., ADMIN)
        //roles są na wszelki wypadek, bo warstwa serwisów i tak ustawia ROLE_USER dla nowego usera
        binder.setDisallowedFields("enabled", "roles");
    }

}