package com.unla.grupo18.controllers;


import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.dto.ProductDtoAdd;
import com.unla.grupo18.dto.ProductDtoUpdate;
import com.unla.grupo18.entities.Product;
import com.unla.grupo18.services.IProductService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.unla.grupo18.helpers.ViewRouteHelper.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;
    private final ModelMapper modelMapper = new ModelMapper();
    public ProductController(IProductService productService) {
        this.productService = productService;
    }


    @GetMapping("")
    String getAllProducts(Model model){
        List<ProductDto> products = productService.getAll();
        model.addAttribute("products", products);
        
        return PRODUCTS;
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("productDto", new ProductDtoAdd());
        
        return PRODUCT_ADD;
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("productDto") ProductDtoAdd productDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "PRODUCT_ADD";
            
        }

        try {
            productService.save(productDto);
            model.addAttribute("successMessage", "Product added successfully!"); // Añade mensaje de éxito al modelo
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error adding product: " + e.getMessage()); // Añade mensaje de error al modelo
           
            return PRODUCT_ADD;
        }
        return RPRODUCT;
       
    }


    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {

        try {
            ProductDtoUpdate productDTO = productService.getProductById(id);
            model.addAttribute("productDTO", productDTO);
        } catch ( Exception e){
            model.addAttribute("error", e.getMessage());
        }

        return PRODUCT_UPDATE;
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                                @ModelAttribute("productDTO")
                                @Valid ProductDtoUpdate productDTO,
                                BindingResult result, Model model)  throws Exception {

        if (result.hasErrors()) {
            model.addAttribute("productDTO", productDTO);
            return PRODUCT_UPDATE;
        }


        try {
            productService.update(productDTO);
            return RPRODUCT;

        }catch (Exception e){
                model.addAttribute("error", e.getMessage());
                return PRODUCT_UPDATE;
            }

    }

    @GetMapping("/active")
    public String getActiveProducts(Model model) {
        model.addAttribute("products", productService.getActiveProducts());
        return PRODUCTS_ACTIVE;
    }

    @GetMapping("/inactive")
    public String getInactiveProducts(Model model) {
        model.addAttribute("products", productService.getInactiveProducts());
        return PRODUCTS_INACTIVE;
    }

    @PostMapping("/deactivate/{id}")
    public String deactivateProduct(@PathVariable Long id) throws Exception {
        productService.deactivateProduct(id);
       return "redirect:/products/active";
       // return PRODUCTS;
    }

    @PostMapping("/activate/{id}")
    public String activateProduct(@PathVariable Long id) throws Exception {
        productService.activateProduct(id);
         return "redirect:/products/inactive";
        //return PRODUCTS;
    }

    @GetMapping("/buy")
    public String buyProducts(Model model) {
        model.addAttribute("products", productService.getActiveProducts());
       
        return PRODUCTS_ACTIVE;
    }












}
