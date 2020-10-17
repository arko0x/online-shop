package com.nikodem.onlineshop.service;

import com.nikodem.onlineshop.domain.Offer;
import com.nikodem.onlineshop.domain.User;
import com.nikodem.onlineshop.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public List<Offer> findAllOffers() {
        return offerRepository.findAll();
    }

    @Override
    public Page<Offer> findPaginatedOffers(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Offer> offers;
        List<Offer> allOffers = offerRepository.findAll();

        if (allOffers.size() < startItem) {
            offers = Collections.emptyList();
        }
        else {
            int toIndex = Math.min(startItem + pageSize, allOffers.size());
            offers = allOffers.subList(startItem, toIndex);
        }

        Page<Offer> offerPage = new PageImpl<>(offers, PageRequest.of(currentPage, pageSize), allOffers.size());

        return offerPage;
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
