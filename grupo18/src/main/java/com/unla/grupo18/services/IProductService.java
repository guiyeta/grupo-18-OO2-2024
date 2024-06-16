package com.unla.grupo18.services;

import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.dto.ProductDtoAdd;
import com.unla.grupo18.entities.Product;

import java.util.List;

public interface IProductService {
    public List<ProductDto> getAll();

    public Product findById(Long id) throws Exception;

    public Product findByName(String name) throws Exception;

    public Product findByCode(String code) throws Exception;

    public Product save(ProductDtoAdd productDto) throws Exception;
    public Product update(ProductDtoAdd productDto) throws Exception;

    public boolean remove(Long id) throws Exception;
}
