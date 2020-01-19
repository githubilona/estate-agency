package com.example.estateagency.services;

import com.example.estateagency.models.Message;
import com.example.estateagency.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }
}
