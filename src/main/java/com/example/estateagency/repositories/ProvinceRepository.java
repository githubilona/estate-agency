package com.example.estateagency.repositories;

import com.example.estateagency.models.OfferType;
import com.example.estateagency.models.Province;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<Province, Integer> {
}