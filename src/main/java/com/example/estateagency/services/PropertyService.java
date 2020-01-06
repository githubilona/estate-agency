package com.example.estateagency.services;

import com.example.estateagency.controllers.commands.PropertyFilter;
import com.example.estateagency.models.Property;
import com.example.estateagency.models.PropertyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PropertyService {

    List<PropertyType> getAllTypes();

    Page<Property> getAllProperties(PropertyFilter filter, Pageable pageable);

    Property getProperty(Long id);

    void deleteProperty(Long id);

    void saveProperty(Property Property);

    Page<Property> getAllPropertiesByUser(Pageable pageable);
}
