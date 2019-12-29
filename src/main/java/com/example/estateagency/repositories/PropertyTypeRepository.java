package com.example.estateagency.repositories;

import com.example.estateagency.models.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyTypeRepository extends JpaRepository<PropertyType, Integer> {
}
