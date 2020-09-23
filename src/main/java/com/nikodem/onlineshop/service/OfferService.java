package com.nikodem.onlineshop.service;

import com.nikodem.onlineshop.domain.Offer;
import com.nikodem.onlineshop.domain.User;

import java.util.List;

public interface OfferService {
    Offer findOfferById(Long id);
    void save(Offer offer);
    List<Offer> findAllUserOffers(User user);
    void delete(Offer offer);
}
