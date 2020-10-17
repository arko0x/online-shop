package com.nikodem.onlineshop.repositories;

import com.nikodem.onlineshop.domain.Offer;
import com.nikodem.onlineshop.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends CrudRepository<Offer, Long> {
    List<Offer> findAllByUser(User user);
    List<Offer> findAll();
}
