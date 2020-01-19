package com.example.estateagency.controllers;

import com.example.estateagency.models.Message;
import com.example.estateagency.models.User;
import com.example.estateagency.services.MessageService;
import com.example.estateagency.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        messageService.save(message);
        return "messageSentSuccess"; // TODO redirect propertyDetails?id=70
    }

    @GetMapping(value = "/messageList.html", params = "id")
    public String showMessageList(Model model, Pageable pageable, long id) {
        model.addAttribute("messageListPage", messageService.getAllReceivedMessagesBySenderUsername(pageable));
        return "messageList";
    }
    @GetMapping("/messageDetails")
    public String showMessageDetails(Model model, long messageId, long userReceiverId) {
        // get all messages for id and clicked converstaion
        User user = userService.getById(userReceiverId);
        Message message = messageService.getById(messageId);
        model.addAttribute("user", user);
        model.addAttribute("message", message);
        return "messageDetails";
    }
}
