package com.nikodem.onlineshop.controllers;

import com.nikodem.onlineshop.domain.Offer;
import com.nikodem.onlineshop.domain.OfferImage;
import com.nikodem.onlineshop.domain.User;
import com.nikodem.onlineshop.service.ImageService;
import com.nikodem.onlineshop.service.OfferService;
import com.nikodem.onlineshop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
@Slf4j
@RequestMapping("/offer")
@SessionAttributes("offer")
public class OfferController {
    private final OfferService offerService;
    private final UserService userService;
    private final ImageService imageService;

    @Autowired
    public OfferController(OfferService offerService, UserService userService, ImageService imageService) {
        this.offerService = offerService;
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    public String showOffer(@PathVariable("id") Long id, Model model) {
        Offer offer = offerService.findOfferById(id);

        model.addAttribute("offer", offer);

        return "offer";
    }


    @GetMapping("/add")
    public String offerAdd(Model model) {
        model.addAttribute("offer", new Offer());

        return "offerAdd";
    }

    @PostMapping("/add")
    public String proceedOffer(@ModelAttribute @Valid Offer offer, BindingResult bindingResult, @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            return "offerAdd";
        }

        offer.setUser(user);
        offer.setPlacedAt(new Date());
     //   offer.setImages(offerImages);
        offer.getImages().forEach(image -> image.setOffer(offer));
        offerService.save(offer);

        return "redirect:/";
    }

    @GetMapping("/userOffers")
    public String showUserOffers(Model model) {
        User currentUser = userService.getCurrentUser();

        List<Offer> userOffers = offerService.findAllUserOffers(currentUser);

        model.addAttribute("userOffers", userOffers);
        model.addAttribute("areThereAnyOffers", !userOffers.isEmpty());

        return "myOffers";
    }

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String deleteOffer(@PathVariable("id") Long id) throws AccessDeniedException {
        Offer offer = offerService.findOfferById(id);
        User currentUser = userService.getCurrentUser();

        if (currentUser.getOffers().contains(offer)) {
            offerService.delete(offer);
            return "redirect:/offer/userOffers";
        }
        else throw new AccessDeniedException("Access denied");
    }

    @GetMapping("/edit/{id}")
    public String showEditOffer(@PathVariable("id") Long id, Model model) throws AccessDeniedException {
        Offer offer = offerService.findOfferById(id);
        User currentUser = userService.getCurrentUser();

        if (currentUser.getOffers().contains(offer)) {
            model.addAttribute("offer", offer);
            return "editOffer";
        }
        else throw new AccessDeniedException("Access denied");
    }

    @RequestMapping(value = "/edit/{id}", method = {RequestMethod.POST, RequestMethod.PUT})
    public String proceedEditOffer(@PathVariable("id") Long id, @Valid Offer offer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editOffer";
        }

        Offer repoOffer = offerService.findOfferById(id);

        copyNotNullProps(offer, repoOffer);

        offerService.save(repoOffer);

        return "redirect:/";
    }

    private void copyNotNullProps(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
