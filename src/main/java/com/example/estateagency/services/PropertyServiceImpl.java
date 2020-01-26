package com.example.estateagency.services;

import com.example.estateagency.controllers.commands.PropertyFilter;
import com.example.estateagency.models.OfferType;
import com.example.estateagency.models.Property;
import com.example.estateagency.models.PropertyType;
import com.example.estateagency.models.User;
import com.example.estateagency.repositories.OfferTypeRepository;
import com.example.estateagency.repositories.PropertyRepository;
import com.example.estateagency.repositories.PropertyTypeRepository;
import com.example.estateagency.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyTypeRepository propertyTypeRepository;
    @Autowired
    private OfferTypeRepository offerTypeRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<PropertyType> getAllTypes() {
        return propertyTypeRepository.findAll();
    }
    @Override
    public List<OfferType> getAllOfferTypes() {
        return offerTypeRepository.findAll();
    }

    @Override
    public Page<Property> getAllProperties(PropertyFilter search, Pageable pageable) {
        if(search.isEmpty()){
            return propertyRepository.findAll(pageable);
        }else{
            return propertyRepository.findAllPropertiesUsingFilter(search.getPhraseLIKE(), search.getMinPrice(), search.getMaxPrice(), pageable);
        }
    }

    @Override
    public Page<Property> getAllPropertiesByUser(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Page page = propertyRepository.findAllByUserId(userRepository.findByUsername(userName).getId(), pageable);
        return page;
    }
    @Override
    public Page<Property> getAllPropertiesByUserId(Pageable pageable, long id) {
        Page page = propertyRepository.findAllByUserId(id, pageable);
        return page;
    }

    @Transactional
    @Override
    public Property getProperty(Long id) {
        if(propertyRepository.existsById(id) == true){
            Optional<Property> optionalProperty = propertyRepository.findById(id);
            return optionalProperty.get();
        }else{
            //obsługa wyjątku
            return null;
        }
    }

    @Override
    public void deleteProperty(Long id) {
    // w przypadku usuwania obsługa wyjątku PropertyNotFoundException nie jest niezbędna dla bezpieczeństwa systemu
        if(propertyRepository.existsById(id) == true){
            propertyRepository.deleteById(id);
        }else{
            //obsługa wyjątku
        }
    }

    @Override
    public void saveProperty(Property property) {
        propertyRepository.save(property);
    }


}
