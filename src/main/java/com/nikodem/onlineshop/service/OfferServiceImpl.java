package com.nikodem.onlineshop.service;

import com.nikodem.onlineshop.domain.Offer;
import com.nikodem.onlineshop.domain.User;
import com.nikodem.onlineshop.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public Offer findOfferById(Long id) {
        return offerRepository.findById(id).isPresent() ? offerRepository.findById(id).get() : null;
    }

    @Override
    public void save(Offer offer) {
        offerRepository.save(offer);
    }

    @Override
    public List<Offer> findAllUserOffers(User user) {
        return offerRepository.findAllByUser(user);
    }

    @Override
    public void delete(Offer offer) {
        offerRepository.delete(offer);
    }
}
