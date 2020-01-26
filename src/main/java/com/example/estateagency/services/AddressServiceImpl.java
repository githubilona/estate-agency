package com.example.estateagency.services;

import com.example.estateagency.models.Address;
import com.example.estateagency.models.PropertyType;
import com.example.estateagency.models.Province;
import com.example.estateagency.repositories.AddressRepository;
import com.example.estateagency.repositories.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    ProvinceRepository provinceRepository;
    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<Province> getAllProvinces() {
        return provinceRepository.findAll();
    }

    @Override
    public void saveAddress(Address address) {
        addressRepository.save(address);
    }
}
