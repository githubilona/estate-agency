package com.example.estateagency.repositories;

import com.example.estateagency.models.Message;
import com.example.estateagency.models.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    // Select messages
    @Query("SELECT m FROM Message m WHERE m.userSender.id != :id AND m.userSender.id != m.userReceiver.id")
    Page<Message> findAllReceivedByUserSenderId(@Param("id") Long id, Pageable pageable); // TODO change name

    @Query("SELECT m FROM Message m WHERE m.conversation.id = :id")
    Page<Message> findDistinctByConversationId(@Param("id") Long id, Pageable pageable);

//    Page<Message> findDistinctByConversation(Pageable pageable);


    @Query(value = "select distinct m.conversation_id from messages m " , nativeQuery = true)
    Page<Message> selectAllConversations(Pageable pageable);

//
//    @Query("SELECT m FROM Message m WHERE m.userSender.id = :id AND m.userSender.id = m.userReceiver.id")
//    Page<Message> findAllByUserSenderId2(@Param("id") Long id, Pageable pageable);

//    @Query("SELECT m FROM Message m WHERE m.userSender.id != :id")
//    Page<Message> findAllByUserSenderId(@Param("id") Long id, Pageable pageable);

//    @Query("SELECT DISTINCT m.property_id, m.user_receiver_id, p.name, p.image_name FROM messages m INNER JOIN properties p ON m.property_id=p.id")

    List<Message> findAllByUserSenderIdAndUserReceiverIdAndPropertyId(Long userSenderId, Long userReceiverId, Long propertyId);
    List<Message> findAllByConversationId(Long id);
//
//    @Query("SELECT m FROM Message m WHERE m.userSender.id != :id AND m.userSender.id != m.userReceiver.id")
//    List<Message> findDistinctByConversationId();
}
