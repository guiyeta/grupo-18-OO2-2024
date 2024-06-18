package com.unla.grupo18.controllers;


import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.dto.ProductDtoAdd;
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
        return "redirect:/products";
        //return PRODUCTS;
    }

/*
    @GetMapping("/update/{id}")
    public String updateProductForm(@PathVariable Long id, Model model) throws Exception {

        try {
            //esto cambiarlo despues -> hacer el mapeo en el service
            //model.addAttribute("productDto",  modelMapper.map( productService.findById(id), ProductDtoAdd.class));
            Product product = productService.findById(id); // Método ficticio para encontrar el producto por ID
            ProductDtoAdd productDto = new ProductDtoAdd();
            productDto.setId(product.getId());
            productDto.setCode(product.getCode());
            productDto.setDescription(product.getDescription());
            productDto.setName(product.getName());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setCriticalStock(product.getStock().getCriticStock());
            model.addAttribute("productDto", productDto);
            return "product/product-update";
            //return PRODUCT_UPDATE;
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Product not found");
            return "redirect:/products";
            //return PRODUCTS;
        }
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute("productDto") ProductDtoAdd productDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "product/product-update";
           // return PRODUCT_UPDATE;
        }
        try {
            productDto.setId(id);
            productService.update(productDto);
            redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating product: " + e.getMessage());
        }

        return "redirect:/products";
        //return PRODUCTS;
    }
    
 */


    @GetMapping("/active")
    public String getActiveProducts(Model model) {
        model.addAttribute("products", productService.getActiveProducts());
        return PRODUCTS_ACTIVE;
    }

    @GetMapping("/inactive")
    public String getInactiveProducts(Model model) {
        model.addAttribute("products", productService.getInactiveProducts());
        // Nombre del archivo HTML de la vista para productos inactivos
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
