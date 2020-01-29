package com.example.estateagency.controllers;

import com.example.estateagency.models.Property;
import com.example.estateagency.models.User;
import com.example.estateagency.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ServletContext servletContext;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/userList.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String showUserList(Model model, Pageable pageable) {
        model.addAttribute("userListPage", userService.getAllUsers(pageable));
        return "userList";
    }
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/delete")
    public String delete(Long id) {

        if (userService.exists(id)) {
            userService.delete(id);
        }
        return "redirect:/userList.html";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/active")
    public String toggleActive(Long id) {
        User user = userService.getById(id);
        user.setEnabled(!user.isEnabled());
        userService.save(user);
        return "redirect:/userList.html";
    }

    @RequestMapping(value="/edit", method=RequestMethod.POST)
    public String processForm(@ModelAttribute("user") User u, BindingResult errors,
                              @RequestParam("file") MultipartFile file) throws IOException {

        if(!file.isEmpty()) {

            String uploadsDir = "/uploads/user-images";
            String realPathToUploads = servletContext.getRealPath(uploadsDir);

            if (!new File(realPathToUploads).exists()) {
                new File(realPathToUploads).mkdir();
            }

            String orgName = file.getOriginalFilename();
            String filePath = realPathToUploads + orgName;
            File dest = new File(filePath);
            file.transferTo(dest);

            u.setImageName(uploadsDir + file.getOriginalFilename());
        }
        if(errors.hasErrors()){
            System.out.println("Errors");
            return "myPropertyList.html";
        }


        userService.editUser(u);

        return "redirect:myPropertyList.html";//po udanym dodaniu/edycji przekierowujemy na listę
    }


    @RequestMapping(value = "/userForm")
    public String userForm() {
        return "userForm";
    }

    @GetMapping("/myAccount")
    public String showMyAccount(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String userName = authentication.getName();
        model.addAttribute("user", userService.getUserByUsername(userName));
        System.out.println("u : " + userName  + " " +userService.getUserByUsername(userName).getUsername() )  ;
        return "fragments/userAccount";
    }
}
