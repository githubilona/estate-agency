package com.example.estateagency.services;

import com.example.estateagency.models.Conversation;
import com.example.estateagency.repositories.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationServiceImpl implements ConversationService {
    @Autowired
    ConversationRepository conversationRepository;

    @Override
    public Conversation save(Conversation conversation) {
        return conversationRepository.save(conversation);
    }
}
