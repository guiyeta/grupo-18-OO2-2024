package com.unla.grupo18.controllers;


import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.entities.Product;
import com.unla.grupo18.services.IProductService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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
    
    @GetMapping("/user/dashboard")
    String userGetAllProducts(Model model){
        List<ProductDto> products = productService.getAll();
        model.addAttribute("products", products);
        return "product/user-product-list";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "product/product-add";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "product/product-add"; // Vuelve al formulario si hay errores de validación
        }

        try {
            productService.save(productDto); // Guarda el producto usando el servicio
            model.addAttribute("successMessage", "Product added successfully!"); // Añade mensaje de éxito al modelo
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error adding product: " + e.getMessage()); // Añade mensaje de error al modelo
            return "product/product-add"; // Vuelve al formulario con el mensaje de error
        }

        return "redirect:/products"; // Redirige a la lista de productos después de agregar exitosamente
    }


    @GetMapping("/update/{id}")
    public String updateProductForm(@PathVariable Long id, Model model) throws Exception {

        try {

            //esto cambiarlo despues
            model.addAttribute("productDto",  modelMapper.map( productService.findById(id),ProductDto.class));
            return "product/product-update";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Product not found"); // Añade mensaje de error si el producto no se encuentra
            return "redirect:/products"; // Redirige a la lista de productos si hay un error
        }
    }


    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "product/product-update"; // Volver a la vista del formulario si hay errores de validación
        }

        try {
            productDto.setId(id); // Establece el ID del producto en el DTO
            productService.update(productDto); // Actualiza el producto usando el servicio
            redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!"); // Añade mensaje de éxito al atributo flash
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating product: " + e.getMessage()); // Añade mensaje de error al atributo flash
        }

        return "redirect:/products"; // Redirigir a la lista de productos después de actualizar
    }

    @PostMapping("/delete/{id}")
    public String removeProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.remove(id); // Llama al servicio para eliminar el producto por su ID
            redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully!"); // Añade mensaje de éxito
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting product: " + e.getMessage()); // Añade mensaje de error
        }
        return "redirect:/products"; // Redirige a la lista de productos después de eliminar
    }
    
   

    

}
