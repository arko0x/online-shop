package com.nikodem.onlineshop.controllers;

import com.nikodem.onlineshop.domain.Offer;
import com.nikodem.onlineshop.domain.User;
import com.nikodem.onlineshop.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.nikodem.onlineshop.repositories.OfferRepository;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/offer")
public class OfferController {
    private OfferRepository offerRepository;
    private UserRepository userRepository;

    @Autowired
    public OfferController(OfferRepository offerRepository, UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public String showOffer(@PathVariable("id") Long id, Model model) {
        Offer offer = offerRepository.findById(id).get();

        model.addAttribute("offer", offer);

        return "offer";
    }


    @GetMapping("/add")
    public String offerAdd(Model model) {
        model.addAttribute("offer", new Offer());

        return "offerAdd";
    }

    @PostMapping("/add")
    public String proceedOffer(@ModelAttribute @Valid Offer offer, BindingResult bindingResult, @AuthenticationPrincipal User user, Model model) {
        if (bindingResult.hasErrors()) {
            return "offerAdd";
        }

        offer.setUser(user);
        offer.setPlacedAt(new Date());
        offerRepository.save(offer);

        return "redirect:/";
    }

    @GetMapping("/userOffers")
    public String showUserOffers(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal instanceof UserDetails ? ((User) principal).getUsername() : principal.toString();
        User currentUser = userRepository.findByUsername(username);

        List<Offer> userOffers = offerRepository.findAllByUser(currentUser);

        model.addAttribute("userOffers", userOffers);
        model.addAttribute("areThereAnyOffers", !userOffers.isEmpty());

        return "myOffers";
    }
}
