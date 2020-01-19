package com.example.estateagency.services;

import com.example.estateagency.models.Message;
import com.example.estateagency.repositories.MessageRepository;
import com.example.estateagency.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }

    @Override
    public Message getById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }


    @Override
    public Page<Message> getAllReceivedMessagesBySenderUsername(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Page page = messageRepository.findAllReceivedByUserSenderId(userRepository.findByUsername(userName).getId(), pageable);
        return page;
    }

}
