package com.nikodem.onlineshop.service;

import com.nikodem.onlineshop.domain.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> getAllBrands();
    Brand findBrandByName(String name);
}
