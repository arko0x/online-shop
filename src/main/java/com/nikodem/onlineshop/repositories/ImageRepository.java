package com.nikodem.onlineshop.repositories;

import com.nikodem.onlineshop.domain.OfferImage;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<OfferImage, Long> {
}
