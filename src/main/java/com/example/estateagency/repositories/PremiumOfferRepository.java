package com.example.estateagency.repositories;

import com.example.estateagency.models.PremiumOffer;
import com.example.estateagency.models.Province;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PremiumOfferRepository extends JpaRepository<PremiumOffer, Integer> {
}