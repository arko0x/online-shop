package com.nikodem.onlineshop.converter;

import com.nikodem.onlineshop.domain.Brand;
import com.nikodem.onlineshop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToBrandConverter implements Converter<String, Brand> {
    private BrandService brandService;

    @Autowired
    public StringToBrandConverter(BrandService brandService) {
        this.brandService = brandService;
    }

    @Override
    public Brand convert(String s)
    {
        Brand brand = brandService.findBrandByName(s);
        return brand;
    }
}
