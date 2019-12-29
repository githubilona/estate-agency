package com.example.estateagency.repositories;

import com.example.estateagency.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
