package com.example.estateagency.services;

import com.example.estateagency.models.Conversation;
import com.example.estateagency.repositories.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationServiceImpl implements ConversationService {
    @Autowired
    ConversationRepository conversationRepository;

    @Override
    public Conversation save(Conversation conversation) {
        return conversationRepository.save(conversation);
    }

    @Override
    public List<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }

    @Override
    public Conversation getById(Long id) {
        return conversationRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Conversation> getAllConversationsForActiveUser(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Page page = conversationRepository.getAllConversationsForActiveUser(userName, pageable);
        return page;
    }
}
