package com.unla.grupo18.services;

import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.dto.ProductDtoAdd;
import com.unla.grupo18.dto.ProductDtoUpdate;
import com.unla.grupo18.entities.Product;

import java.util.List;

public interface IProductService {

    public List<ProductDto> getAll();

    public Product findById(Long id);

    public Product findByName(String name);

    public Product findByCode(String code);

    public Product save(ProductDtoAdd productDto) throws Exception;

    //public Product update(ProductDtoAdd productDto) throws Exception;

    public Product update(ProductDtoUpdate productDto) throws Exception;

    public boolean remove(Long id) throws Exception;

    public List<ProductDto> getActiveProducts();

    public List<ProductDto> getInactiveProducts();

    public void deactivateProduct(Long productId) throws Exception ;

    public void activateProduct(Long productId) throws Exception;

    public ProductDtoUpdate getProductById(Long id);

}
