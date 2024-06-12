package com.unla.grupo18.services.implementation;


import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.entities.Product;
import com.unla.grupo18.exceptions.ProductNotFoundException;
import com.unla.grupo18.repositories.IProductRepository;
import com.unla.grupo18.services.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> findByPriceLessThanOrEqual(double maxPrice) throws Exception {
        //TODO: Manejar excepcion? SI esta vacio por ejemplo
        List<Product> products = productRepository.findByPriceLessThanOrEqual(maxPrice);

        if (products.isEmpty()){
            throw new Exception("There are no products whose value is less than " + maxPrice);
        }

        return products
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAll() {
        List<Product> products = productRepository.findAll();
        return products
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Product findById(Long id) throws Exception {
        return productRepository.findById(id)
                .orElseThrow(() -> new Exception("Product not found with id " + id));
    }

    @Override
    public ProductDto findByName(String name) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findByName(name);
        if (product.isPresent()) {
            return modelMapper.map(product.get(),ProductDto.class);
        } else {
            throw new ProductNotFoundException("Product with name " + name + " not found");
        }
    }

    @Override
    public ProductDto findByCode(String code) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findByCode(code);
        if (product.isPresent()) {
            return (modelMapper.map(product.get(), ProductDto.class));
        } else {
            throw new ProductNotFoundException("Product with code " + code + " not found");
        }
    }


    public Product save(ProductDto productDto) throws Exception {
        Product product = modelMapper.map(productDto, Product.class);
         return productRepository.save(product);

    }


    @Override
    public ProductDto update(Product product) throws Exception {
        // TODO: Realizar validaciones
        return modelMapper.map(productRepository.save(product),ProductDto.class);
    }


    @Override
    public boolean remove(Long id) throws ProductNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        productRepository.deleteById(id);
        return true;
    }
}
