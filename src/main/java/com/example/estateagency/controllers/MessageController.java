package com.example.estateagency.controllers;

import com.example.estateagency.models.Conversation;
import com.example.estateagency.models.Message;
import com.example.estateagency.models.User;
import com.example.estateagency.repositories.ConversationRepository;
import com.example.estateagency.services.ConversationService;
import com.example.estateagency.services.MessageService;
import com.example.estateagency.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConversationService conversationService;



    @PostMapping(value="/propertyDetails.html")
    public String sendMessage(@ModelAttribute Message message){
        if(message.getUserSender() == null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User userSender =userService.getUserByUsername(userName);
            message.setUserSender(userService.getUserByUsername(userName));

            System.out.println("Message property   " + message.getProperty());
            System.out.println("id S "+userSender.getId()+"id R " +message.getUserReceiver().getId());

            List<Message> sentMessagesList = messageService.findAllByUserSenderIdAndUserReceiverIdAndPropertyId(
                    userSender.getId(),message.getUserReceiver().getId(), message.getProperty().getId());
            List<Message> replyMessageList = messageService.findAllByUserSenderIdAndUserReceiverIdAndPropertyId(
                    message.getUserReceiver().getId(),userSender.getId(), message.getProperty().getId());

            if(sentMessagesList.isEmpty() && replyMessageList.isEmpty()){
                // create new conversation
                Conversation c = conversationService.save(new Conversation());
                message.setConversation(c);
                System.out.println("create new conversation ");
            }else if(sentMessagesList.isEmpty()){
                Conversation c = replyMessageList.get(0).getConversation();
                message.setConversation(c);
            }else if(replyMessageList.isEmpty()){
                Conversation c = sentMessagesList.get(0).getConversation();
                message.setConversation(c);
            }
            messageService.save(message);
        }

        return "messageSentSuccess"; // TODO redirect propertyDetails?id=70 or conversation
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
