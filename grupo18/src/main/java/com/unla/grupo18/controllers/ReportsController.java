package com.unla.grupo18.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unla.grupo18.dto.PurchaseOrderDto;
import com.unla.grupo18.dto.UserPurchaseDto;
import com.unla.grupo18.services.IProductService;
import com.unla.grupo18.services.IPurchaseOrderService;
import com.unla.grupo18.services.IUserPurchaseService;
import com.unla.grupo18.entities.PurchaseOrder;

@Controller
@RequestMapping("/reports")
public class ReportsController {
	
	@Autowired
    private IPurchaseOrderService purchaseOrderService;

    @Autowired
    private  IUserPurchaseService userPurchaseService;

   
    
    
   
     
    @GetMapping("")
    String getAll(Model model){
    	 List<PurchaseOrderDto> orders = purchaseOrderService.findAll();
    	 List<UserPurchaseDto> customerPurchases = userPurchaseService.findAll();
    	 model.addAttribute("orders",orders );
    	 model.addAttribute("customerPurchases",customerPurchases );
    	return "reports/reports";
    	
    }

	

}
