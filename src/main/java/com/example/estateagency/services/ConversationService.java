package com.example.estateagency.services;

import com.example.estateagency.models.Conversation;
import com.example.estateagency.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConversationService {
    Conversation save(Conversation conversation);
    List<Conversation> getAllConversations();
    Conversation getById(Long id);
    Page<Conversation> getAllConversationsForActiveUser(Pageable pageable);


}
