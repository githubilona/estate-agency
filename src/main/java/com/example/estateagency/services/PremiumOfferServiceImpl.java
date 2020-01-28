package com.example.estateagency.services;

import com.example.estateagency.models.PremiumOffer;
import com.example.estateagency.repositories.PremiumOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PremiumOfferServiceImpl implements PremiumOfferService {

    @Autowired
    private PremiumOfferRepository premiumOfferRepository;

    @Override
    public List<PremiumOffer> getAllPremiumOffer() {
        return premiumOfferRepository.findAll();
    }
}
