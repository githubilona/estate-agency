package com.example.estateagency.repositories;

import com.example.estateagency.models.Message;
import com.example.estateagency.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
