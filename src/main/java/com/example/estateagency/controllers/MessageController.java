package com.example.estateagency.controllers;

import com.example.estateagency.models.Conversation;
import com.example.estateagency.models.Meeting;
import com.example.estateagency.models.Message;
import com.example.estateagency.models.User;
import com.example.estateagency.repositories.ConversationRepository;
import com.example.estateagency.repositories.MessageRepository;
import com.example.estateagency.services.ConversationService;
import com.example.estateagency.services.MeetingService;
import com.example.estateagency.services.MessageService;
import com.example.estateagency.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MeetingService meetingService;


    @PostMapping(value="/propertyDetails.html")
    public String sendMessage(@ModelAttribute Message message){
        if(message.getUserSender() == null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User userSender =userService.getUserByUsername(userName);
            message.setUserSender(userService.getUserByUsername(userName));

            System.out.println("Message property   " + message.getProperty());
            System.out.println("id S "+userSender.getId()+"id R " +message.getUserReceiver().getId());

            // TODO use conversationService
            List<Message> sentMessagesList = messageService.findAllByUserSenderIdAndUserReceiverIdAndPropertyId(
                    userSender.getId(),message.getUserReceiver().getId(), message.getProperty().getId());
            List<Message> replyMessageList = messageService.findAllByUserSenderIdAndUserReceiverIdAndPropertyId(
                    message.getUserReceiver().getId(),userSender.getId(), message.getProperty().getId());

            if(sentMessagesList.isEmpty() && replyMessageList.isEmpty()){
                // create new conversation
                Conversation newConversation = new Conversation();
                newConversation.setUserSender(userSender);
                newConversation.setUserReceiver(message.getUserReceiver());
                newConversation.setProperty(message.getProperty());

                Conversation c = conversationService.save(newConversation);
                message.setConversation(c);
                System.out.println("create new conversation ");
            }else if(sentMessagesList.isEmpty()){
                Conversation c = replyMessageList.get(0).getConversation();
                System.out.println(" 2 - " + c.getId());
                message.setConversation(c);
            }else if(replyMessageList.isEmpty()){
                Conversation c = sentMessagesList.get(0).getConversation();
                System.out.println(" 3 - " + c.getId());
                message.setConversation(c);
            }else{
                System.out.println( "5- sent " +sentMessagesList.isEmpty());
                System.out.println("4 rep "+ replyMessageList.isEmpty());
                Conversation c = replyMessageList.get(0).getConversation();
                message.setConversation(c);

            }
            messageService.save(message);
        }

        return "redirect:/messageDetails?conversationId="+message.getConversation().getId();
    }
    @PostMapping(value="/sendReply")
    public String sendReply(@ModelAttribute Message message){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        if(message.getUserReceiver().getUsername().equals(userName)){
            User sender = message.getUserSender();
            User receiver = message.getUserReceiver();
            message.setUserSender(receiver);
            message.setUserReceiver(sender);
        }
        messageService.save(message);
        return "redirect:/messageDetails?conversationId="+message.getConversation().getId();
    }

    @GetMapping(value = "/messageList.html")
    public String showMessageList(Model model, Pageable pageable) {
        model.addAttribute("conversationListPage", conversationService.getAllConversationsForActiveUser(pageable));
        return "messageList";
    }
    @GetMapping(value = "/contactList.html")
    public String showContactList(Model model, Pageable pageable) {
        model.addAttribute("conversationListPage", conversationService.getAllConversationsForActiveUser(pageable));
        model.addAttribute("meeting", new Meeting());
        model.addAttribute("meetings",meetingService.getAllMeetings());
        return "contactList";
    }

    @PostMapping("/saveMeeting")
    public String saveMeeting(@ModelAttribute Meeting meeting, @RequestParam("conversationId") Long conversationId, Model model, Pageable pageable){
        Conversation c = conversationService.getById(conversationId);
        meeting.setConversation(conversationService.getById(conversationId));
        meetingService.save(meeting);
        model.addAttribute("conversationListPage", conversationService.getAllConversationsForActiveUser(pageable));
        model.addAttribute("meetings",meetingService.getAllMeetings());
        return "redirect:/contactList.html";
    }
    @GetMapping("/messageDetails")
    public String showMessageDetails(Model model,Long conversationId) {
        Conversation c = conversationService.getById(conversationId);
        System.out.println("conversation id assigned to a new msg " + c.getId());
        Message message = new Message();
        message.setConversation(c);
        message.setUserReceiver(c.getUserSender());
        message.setUserSender(c.getUserReceiver());
        message.setProperty(c.getProperty());

        List<Message> messagesList = messageService.findAllByConversationId(conversationId);
        model.addAttribute("messagesList", messagesList);
        model.addAttribute("message", message);

        return "messageDetails";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {//Rejestrujemy edytory właściwości
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, false);
        binder.registerCustomEditor(Date.class, "meetingDate", dateEditor);
    }
}
