package com.unla.grupo18.controllers;


import com.unla.grupo18.dto.ProductDtoAdd;

import com.unla.grupo18.dto.StockDto;
import com.unla.grupo18.entities.Stock;
import com.unla.grupo18.services.IStockService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.unla.grupo18.helpers.ViewRouteHelper.STOCK;
import static com.unla.grupo18.helpers.ViewRouteHelper.STOCK_UPDATE;;


@Controller
@RequestMapping("/stock")
public class StockController {

    private final IStockService stockService;
    private final ModelMapper modelMapper = new ModelMapper();


    public StockController(IStockService stockService) {
        this.stockService = stockService;
    }

    /*
    @GetMapping("")
    String getAllStocks(Model model){
        List<StockDto> stocks = stockService.findAll();
        model.addAttribute("stocks", stocks);
        return "stock/stock-list";
    }
    */

    @GetMapping("")
    String getAllActiveStocks(Model model){
        List<StockDto> stocks = stockService.findStocksWithActiveProduct();
        model.addAttribute("stocks", stocks);
        return STOCK;
    }

    @GetMapping("/update/{id}")
    public String showEditCriticStockForm(@PathVariable Long id, Model model) {
        Stock stock = stockService.findById(id); // Implement this method in StockService
        StockDto stockDto = modelMapper.map(stock,StockDto.class);
        model.addAttribute("stockDto", stockDto);
        return STOCK_UPDATE;
    }

    @PostMapping("/update/{id}")
    public String updateCriticStock(@PathVariable Long id,
                                    @ModelAttribute("stockDto") @Valid  StockDto stockDto,
                                    BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            return STOCK_UPDATE;
        }

        stockService.update(stockDto);
        return "redirect:/stock";
    }



}
