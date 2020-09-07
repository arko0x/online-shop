package com.nikodem.onlineshop.controllers;

import com.nikodem.onlineshop.domain.Offer;
import com.nikodem.onlineshop.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private OfferRepository offerRepository;

    @Autowired
    public HomeController(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @GetMapping
    public String getHomePage(Model model) {
        List<Offer> offers = (List<Offer>) offerRepository.findAll();
        offers.sort(Comparator.reverseOrder());

        model.addAttribute("offers", offers);
        model.addAttribute("areThereAnyOffers", !offers.isEmpty());

        return "index";
    }
}
