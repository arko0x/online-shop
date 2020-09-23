package com.nikodem.onlineshop.service;

import com.nikodem.onlineshop.domain.OfferImage;
import com.nikodem.onlineshop.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void save(OfferImage offerImage) {
        imageRepository.save(offerImage);
    }
}
