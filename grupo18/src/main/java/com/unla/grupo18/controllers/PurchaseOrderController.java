package com.unla.grupo18.controllers;


import com.unla.grupo18.dto.PurchaseOrderDto;

import com.unla.grupo18.entities.PurchaseOrder;
import com.unla.grupo18.services.IPurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/purchase-order")
public class PurchaseOrderController {

    private final IPurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(IPurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping("")
    String getAllPurchaseOrders(Model model){
        List<PurchaseOrderDto> orders = purchaseOrderService.findAll();
        model.addAttribute("orders", orders);
        return "purchaseOrder/purchaseOrder-list";
    }


    @GetMapping("/add")
    public String showPurchaseOrderForm(
                                        @RequestParam(value = "productName", required = false) String productName,
                                        Model model) {
        PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto();


        if (productName != null) {
            purchaseOrderDto.setProductName(productName);
        }

        model.addAttribute("purchaseOrderDto", purchaseOrderDto);
        return "purchaseOrder/purchaseOrder-add";
    }



    @PostMapping("/add")
    public String addPurchaseOrder(@Valid @ModelAttribute("purchaseOrderDto") PurchaseOrderDto purchaseOrderDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "purchaseOrder/purchaseOrder-add";
        }

        try {
            PurchaseOrder savedPurchaseOrder = purchaseOrderService.save(purchaseOrderDto);
            model.addAttribute("successMessage", "Purchase Order added successfully with ID: " + savedPurchaseOrder.getId());
            return "redirect:/purchase-order/add?success";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error adding Purchase Order: " + e.getMessage());
            return "purchaseOrder/purchaseOrder-add";
        }


    }



}
