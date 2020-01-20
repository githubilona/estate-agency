package com.example.estateagency.services;

import com.example.estateagency.models.Message;
import com.example.estateagency.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {
    void save(Message message);
    Message getById(Long id);
    Page<Message> getAllReceivedMessagesBySenderUsername(Pageable pageable);

    List<Message> findAllByUserSenderIdAndUserReceiverIdAndPropertyId(Long userSenderId, Long userReceiverId, Long propertyId);
}
