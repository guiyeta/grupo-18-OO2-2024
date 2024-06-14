package com.unla.grupo18.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.entities.PurchaseProduct;
import com.unla.grupo18.services.IPurchaseProductService;
import com.unla.grupo18.services.implementation.PurchaseProductService;

@Controller
@RequestMapping("/purchase")
public class PurchaseProductController {
	
	private final PurchaseProductService purchaseProductService;
    private final ModelMapper modelMapper = new ModelMapper();
    
    
    public PurchaseProductController(PurchaseProductService purchaseProductService) {
    	this.purchaseProductService=purchaseProductService;
    }
    
    @GetMapping("")
    String getProductsToPurchase(Model model) {
    	return "/purchase/purchase";
    }
   
    
}
