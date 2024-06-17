package com.unla.grupo18.controllers;


import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.dto.ProductDtoAdd;
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
        return "product/product-list";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProductForm(Model model) {
        model.addAttribute("productDto", new ProductDtoAdd());
        return "product/product-add";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("productDto") ProductDtoAdd productDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "product/product-add";
        }

        try {
            productService.save(productDto);
            model.addAttribute("successMessage", "Product added successfully!"); // Añade mensaje de éxito al modelo
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error adding product: " + e.getMessage()); // Añade mensaje de error al modelo
            return "product/product-add"; // Vuelve al formulario con el mensaje de error
        }

        return "redirect:/products";
    }


    @GetMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateProductForm(@PathVariable Long id, Model model) throws Exception {

        try {
            //esto cambiarlo despues -> hacer el mapeo en el service
            model.addAttribute("productDto",  modelMapper.map( productService.findById(id), ProductDtoAdd.class));
            return "product/product-update";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Product not found");
            return "redirect:/products";
        }
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute("productDto") ProductDtoAdd productDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "product/product-update";
        }

        try {
            productDto.setId(id);
            productService.update(productDto);
            redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!"); // Añade mensaje de éxito al atributo flash
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating product: " + e.getMessage()); // Añade mensaje de error al atributo flash
        }

        return "redirect:/products";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/delete/{id}")
    public String removeProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.remove(id);
            redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully!"); // Añade mensaje de éxito
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting product: " + e.getMessage()); // Añade mensaje de error
        }
        return "redirect:/products";
    }


    @GetMapping("/active")
    public String getActiveProducts(Model model) {
        model.addAttribute("products", productService.getActiveProducts());
        return "product/product-active-list"; // Nombre del archivo HTML de la vista para productos activos
    }

    @GetMapping("/inactive")
    public String getInactiveProducts(Model model) {
        model.addAttribute("products", productService.getNotActiveProducts());
        return "product/product-inactive-list"; // Nombre del archivo HTML de la vista para productos inactivos
    }

    @PostMapping("/deactivate/{id}")
    public String deactivateProduct(@PathVariable Long id) throws Exception {
        productService.deactivateProduct(id);
        return "redirect:/products";
    }

    @PostMapping("/activate/{id}")
    public String activateProduct(@PathVariable Long id) throws Exception {
        productService.activateProduct(id);
        return "redirect:/products";
    }









}
