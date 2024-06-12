package com.unla.grupo18.controllers;

import com.unla.grupo18.dto.StockDto;
import com.unla.grupo18.services.implementation.StockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StockController {

    /*
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

     */

    @GetMapping("/stocks")
    public String getStocks(Model model) {

       // List<StockDto> stocks = stockService.getAll();
        List<StockDto> stocks = new ArrayList<>();
        stocks.add(new StockDto(10, 5, "Producto A"));
        stocks.add(new StockDto(15, 8, "Producto B"));
        stocks.add(new StockDto(20, 10, "Producto C"));

        model.addAttribute("stocks", stocks);
        return "stock/stock";
    }
}
