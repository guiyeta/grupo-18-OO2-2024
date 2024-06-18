package com.unla.grupo18.controllers;

import com.unla.grupo18.dto.ProductDto;

import com.unla.grupo18.dto.PurchaseOrderDto;
import com.unla.grupo18.dto.UserPurchaseDto;
import com.unla.grupo18.dto.UserPurchaseDtoAdd;
import com.unla.grupo18.entities.Product;
import com.unla.grupo18.entities.PurchaseOrder;
import com.unla.grupo18.entities.UserPurchase;
import com.unla.grupo18.services.IProductService;
import com.unla.grupo18.services.IUserPurchaseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.unla.grupo18.helpers.ViewRouteHelper.USER_PURCHASE;
import static com.unla.grupo18.helpers.ViewRouteHelper.USER_PURCHASE_ADD;

@Controller
@RequestMapping("/user-purchase")
public class UserPurchaseController {

    private final IUserPurchaseService userPurchaseService;
    private final IProductService productService;

    public UserPurchaseController(IUserPurchaseService userPurchaseService, IProductService productService) {
        this.userPurchaseService = userPurchaseService;
        this.productService = productService;
    }


    @GetMapping("")
    String getAllUserPurchases(Model model){
        List<UserPurchaseDto> purchases = userPurchaseService.findAll();
        model.addAttribute("purchases", purchases);
        return USER_PURCHASE;
    }



    @GetMapping("/add")
    public String showUserPurchaseForm(
            @RequestParam(value = "productName", required = true) String productName,
            Model model) throws Exception {
        UserPurchaseDto userPurchaseDto = new UserPurchaseDto();


        if (productName != null) {
            userPurchaseDto.setProductName(productName);
        }
        

        model.addAttribute("userPurchaseDto", userPurchaseDto);
        return USER_PURCHASE_ADD;
    }

    @PostMapping("/add")
    public String addUserPurchase(@Valid @ModelAttribute("userPurchaseDto") UserPurchaseDtoAdd userPurchaseDto, BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            return USER_PURCHASE_ADD;
        }

        Product product = productService.findByName(userPurchaseDto.getProductName());
        double totalPrice = product.getSellPrice() * userPurchaseDto.getAmount();
        model.addAttribute(totalPrice);
        try {
            UserPurchase savedUserPurchase = userPurchaseService.save(userPurchaseDto);
            model.addAttribute("successMessage", "Purchase added successfully: " + savedUserPurchase.getId());
            return "redirect:/products/buy";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error:" + e.getMessage());
            return USER_PURCHASE_ADD;
        }
    }



}
