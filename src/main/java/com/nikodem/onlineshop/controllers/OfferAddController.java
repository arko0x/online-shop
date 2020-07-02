package com.nikodem.onlineshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OfferAddController {
    @GetMapping("/addOffer")
    public String offerAdd() {
        return "offerAdd";
    }
}
