package com.unla.grupo18.controllers;

import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.dto.PurchaseOrderDto;
import com.unla.grupo18.dto.UserPurchaseDto;
import com.unla.grupo18.dto.UserPurchaseDtoAdd;
import com.unla.grupo18.entities.PurchaseOrder;
import com.unla.grupo18.entities.UserPurchase;
import com.unla.grupo18.services.IUserPurchaseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user-purchase")
public class UserPurchaseController {

    private final IUserPurchaseService userPurchaseService;

    public UserPurchaseController(IUserPurchaseService userPurchaseService) {
        this.userPurchaseService = userPurchaseService;
    }


    @GetMapping("")
    String getAllUserPurchases(Model model){
        List<UserPurchaseDto> purchases = userPurchaseService.findAll();
        model.addAttribute("purchases", purchases);
        return "userPurchase/userPurchase-list";
    }



    @GetMapping("/add")
    public String showUserPurchaseForm(
            @RequestParam(value = "productName", required = true) String productName,
            Model model) {
        UserPurchaseDto userPurchaseDto = new UserPurchaseDto();


        if (productName != null) {
            userPurchaseDto.setProductName(productName);
        }

        model.addAttribute("userPurchaseDto", userPurchaseDto);
        return "userPurchase/userPurchase-add";
    }

    @PostMapping("/add")
    public String addUserPurchase(@Valid @ModelAttribute("userPurchaseDto") UserPurchaseDtoAdd purchaseOrderDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "userPurchase/userPurchase-add";
        }

        try {
            UserPurchase savedUserPurchase = userPurchaseService.save(purchaseOrderDto);
            model.addAttribute("successMessage", "Purchase added successfully with ID: " + savedUserPurchase.getId());
            return "redirect:/products";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error adding Purchase Order: " + e.getMessage());
            return "userPurchase/userPurchase-add";
        }


    }

}
