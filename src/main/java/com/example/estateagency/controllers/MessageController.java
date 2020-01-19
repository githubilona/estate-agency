package com.example.estateagency.controllers;

import com.example.estateagency.models.Message;
import com.example.estateagency.services.MessageService;
import com.example.estateagency.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping(value="/propertyDetails.html")
    public String sendMessage(@ModelAttribute Message message){
        if(message.getUserSender() == null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            message.setUserSender(userService.getUserByUsername(userName));
        }

//        Property p = propertyService.getProperty(id);
//        message.setProperty(10);
//        message.setUserReceiver(p.getUser());

       messageService.save(message);
        return "messageSentSuccess"; // TODO redirect propertyDetails?id=70
    }
}
