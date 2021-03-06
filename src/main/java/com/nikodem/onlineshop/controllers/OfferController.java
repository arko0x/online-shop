package com.nikodem.onlineshop.controllers;

import com.nikodem.onlineshop.domain.Offer;
import com.nikodem.onlineshop.domain.User;
import com.nikodem.onlineshop.service.BrandService;
import com.nikodem.onlineshop.service.OfferService;
import com.nikodem.onlineshop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Slf4j
@RequestMapping("/offer")
@SessionAttributes({"offer", "brands"})
public class OfferController {
    private final OfferService offerService;
    private final UserService userService;
    private final BrandService brandService;

    @Autowired
    public OfferController(OfferService offerService, UserService userService, BrandService brandService) {
        this.offerService = offerService;
        this.userService = userService;
        this.brandService = brandService;
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
        model.addAttribute("brands", brandService.getAllBrands());

        return "offerAdd";
    }

    @PostMapping("/add")
    public String proceedOffer(@ModelAttribute @Valid Offer offer, BindingResult bindingResult, @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            return "offerAdd";
        }

        offer.setUser(user);
        offer.setPlacedAt(new Date());
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

    @GetMapping("/offers")
    public String getOffersList(Model model, @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Offer> offerPage = offerService.findPaginatedOffers(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("offerPage", offerPage);

        int totalPages = offerPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "offersPage";
    }
}
