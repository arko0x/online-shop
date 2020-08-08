package com.nikodem.onlineshop.controllers;

import com.nikodem.onlineshop.domain.Offer;
import com.nikodem.onlineshop.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nikodem.onlineshop.repositories.OfferRepository;

@Controller
@Slf4j
@RequestMapping("/offer")
public class OfferAddController {
    private OfferRepository offerRepository;

    @Autowired
    public OfferAddController(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }


    @GetMapping("/add")
    public String offerAdd(Model model) {
        model.addAttribute("offer", new Offer());

        log.info("IN GET MAPPING XD");

        return "offerAdd";
    }

    @PostMapping("/add")
    public String proceedOffer(@ModelAttribute Offer offer, @AuthenticationPrincipal User user, Model model) {
        offer.setUser(user);
        offerRepository.save(offer);

        return "redirect:/";
    }
}
