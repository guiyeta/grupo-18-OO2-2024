package com.unla.grupo18.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unla.grupo18.dto.StockDto;
import com.unla.grupo18.services.IStockService;



@Controller
@RequestMapping("stock")
public class StockController {

	private final IStockService stockService;
	private final ModelMapper modelMapper = new ModelMapper();
    public StockController(IStockService stockService) {
        this.stockService= stockService;
        
    }
    
	@GetMapping("")
    String getAllProducts(Model model){
        List<StockDto> products = stockService.getAll();
        model.addAttribute("products", products);
        return "stock/stock-list";
        
    }
}
