package com.nikodem.onlineshop.repositories;

import com.nikodem.onlineshop.domain.Brand;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BrandRepository extends CrudRepository<Brand, Long> {
    List<Brand> findAll();
    Brand findByName(String name);
}
