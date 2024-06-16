package com.unla.grupo18.controllers;


import com.unla.grupo18.dto.StockDto;
import com.unla.grupo18.services.IStockService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/stock")
public class StockController {

    private final IStockService stockService;
    private final ModelMapper modelMapper = new ModelMapper();


    public StockController(IStockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("")
    String getAllStocks(Model model){
        List<StockDto> stocks = stockService.findAll();
        model.addAttribute("stocks", stocks);
        return "stock/stock-list";
    }


}
