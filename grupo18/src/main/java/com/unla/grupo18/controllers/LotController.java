package com.unla.grupo18.controllers;


import com.unla.grupo18.dto.LotDto;
import com.unla.grupo18.dto.LotDtoAdd;
import com.unla.grupo18.entities.Lot;
import com.unla.grupo18.services.ILotService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/lots")
public class LotController {

    private final ILotService lotService;

    public LotController(ILotService lotService) {
        this.lotService = lotService;
    }

    @GetMapping("")
    String getAllLots(Model model){
        List<LotDto> lots = lotService.findAll();
        model.addAttribute("lots", lots);
        return "lot/lot-list";
    }


    @GetMapping("/add")
    public String addLotForm(Model model) {
        model.addAttribute("lotDto", new LotDtoAdd());
        return "lot/lot-add";
    }

    @PostMapping("/add")
    public String addLot(@Valid @ModelAttribute("lotDto") LotDtoAdd lotDtoAdd, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "lot/lot-add";
        }

        try {
            Lot savedLot = lotService.save(lotDtoAdd);
            model.addAttribute("successMessage", "Lot  added successfully with ID: " + lotDtoAdd.getId());
            return "redirect:/lots/add?success";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error adding lot : " + e.getMessage());
            return "lot/lot-add";
        }


    }
}
