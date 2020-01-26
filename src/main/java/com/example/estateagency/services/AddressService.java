package com.example.estateagency.services;

import com.example.estateagency.models.Address;
import com.example.estateagency.models.Province;

import java.util.List;

public interface AddressService {
    List<Province> getAllProvinces();
    void saveAddress(Address address);

}
