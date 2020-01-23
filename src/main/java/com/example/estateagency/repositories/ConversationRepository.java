package com.example.estateagency.repositories;

import com.example.estateagency.models.Conversation;
import com.example.estateagency.models.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query("SELECT c FROM Conversation c WHERE c.userSender.username = :activeUser OR c.userReceiver.username = :activeUser")
    Page<Conversation> getAllConversationsForActiveUser(@Param("activeUser") String activeUser, Pageable pageable);
}
