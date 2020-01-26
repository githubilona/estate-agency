package com.example.estateagency.repositories;

import com.example.estateagency.models.OfferType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferTypeRepository extends JpaRepository<OfferType, Integer> {
}