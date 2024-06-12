package com.unla.grupo18.controllers;


import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.services.implementation.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String getProducts(Model model) {

        List<ProductDto> products = productService.getAll();
        model.addAttribute("products", products);
        return "products/products";
    }

    @GetMapping("/add-product")
    public String showAddProductForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "products/add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute ProductDto productDto, Model model) {
        try {
            productService.save(productDto);
            model.addAttribute("successMessage", "Product added successfully!");
            return "redirect:/products";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error adding product: " + e.getMessage());
        }
        return "products/add-product";
    }


    @DeleteMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        try {
            productService.remove(productId);

            return "redirect:/products";
        } catch (Exception e) {

            e.printStackTrace();
            return "redirect:/products";
        }
    }

}
