package com.nikodem.onlineshop.controllers;

import com.nikodem.onlineshop.domain.Offer;
import com.nikodem.onlineshop.domain.User;
import com.nikodem.onlineshop.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
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
import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@Slf4j
@RequestMapping("/offer")
@SessionAttributes("offer")
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

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String deleteOffer(@PathVariable("id") Long id, Model model) throws AccessDeniedException {
        Offer offer = offerRepository.findById(id).get();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal instanceof UserDetails ? ((User) principal).getUsername() : principal.toString();
        User currentUser = userRepository.findByUsername(username);

        if (currentUser.getOffers().contains(offer)) {
            offerRepository.delete(offer);
            return "redirect:/offer/userOffers";
        }
        else throw new AccessDeniedException("Access denied");
    }

    @GetMapping("/edit/{id}")
    public String showEditOffer(@PathVariable("id") Long id, Model model) throws AccessDeniedException {
        Offer offer = offerRepository.findById(id).get();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal instanceof UserDetails ? ((User) principal).getUsername() : principal.toString();
        User currentUser = userRepository.findByUsername(username);

        if (currentUser.getOffers().contains(offer)) {
            model.addAttribute("offer", offer);
            return "editOffer";
        }
        else throw new AccessDeniedException("Access denied");
    }

    @RequestMapping(value = "/edit/{id}", method = {RequestMethod.POST, RequestMethod.PUT})
    public String proceedEditOffer(@PathVariable("id") Long id, @Valid Offer offer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "editOffer";
        }

        Offer repoOffer = offerRepository.findById(id).get();

        copyNotNullProps(offer, repoOffer);

        offerRepository.save(repoOffer);

        return "redirect:/";
    }

    private void copyNotNullProps(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
