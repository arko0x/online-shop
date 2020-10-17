package com.nikodem.onlineshop.service;

import com.nikodem.onlineshop.domain.Offer;
import com.nikodem.onlineshop.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OfferService {
    Offer findOfferById(Long id);
    void save(Offer offer);
    List<Offer> findAllUserOffers(User user);
    void delete(Offer offer);
    List<Offer> findAllOffers();
    Page<Offer> findPaginatedOffers(Pageable pageable);
}
