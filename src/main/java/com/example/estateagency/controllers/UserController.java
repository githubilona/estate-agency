package com.example.estateagency.controllers;

import com.example.estateagency.models.Property;
import com.example.estateagency.models.User;
import com.example.estateagency.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

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
    @GetMapping(value="/userDetails.html", params = "id")
    public String showUserDetails(Model model, long id){
        User u = userService.getById(id);
        //obłużyć not found exception
        model.addAttribute("user", u);
        return "userDetails";
    }
}
