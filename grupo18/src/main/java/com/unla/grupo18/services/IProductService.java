package com.unla.grupo18.services;

import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.entities.Product;
import com.unla.grupo18.exceptions.ProductNotFoundException;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IProductService {



    public List<ProductDto> findByPriceLessThanOrEqual(double maxPrice) throws Exception;

    public List<ProductDto> getAll();

    public Product findById(Long id) throws Exception;

    public ProductDto findByName(String name) throws ProductNotFoundException;

    public ProductDto findByCode(String code) throws ProductNotFoundException;


    public Product save(ProductDto productDto) throws Exception;
    public Product update(ProductDto productDto) throws Exception;

    public boolean remove(Long id) throws ProductNotFoundException;

	






}
