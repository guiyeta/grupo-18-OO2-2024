package com.unla.grupo18.controllers;

import com.unla.grupo18.dto.PurchaseOrderDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PurchaseOrderController {

    @GetMapping("/purchase-orders")
    public String getPurchaseOrders(Model model) {

        List<PurchaseOrderDto> orders = new ArrayList<>();
        orders.add(new PurchaseOrderDto("Product A", 10, LocalDate.now(), "Pending", "Supplier X"));
        orders.add(new PurchaseOrderDto("Product B", 15, LocalDate.now().minusDays(2), "Completed", "Supplier Y"));
        orders.add(new PurchaseOrderDto("Product C", 20, LocalDate.now().minusDays(5), "Processing", "Supplier Z"));


        model.addAttribute("orders", orders);


        return "purchaseOrder/purchaseOrder";
    }
}
