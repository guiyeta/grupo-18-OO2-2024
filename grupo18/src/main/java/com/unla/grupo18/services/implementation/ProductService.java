package com.unla.grupo18.services.implementation;



import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.dto.ProductDtoAdd;
import com.unla.grupo18.entities.Product;
import com.unla.grupo18.entities.Stock;
import com.unla.grupo18.repositories.IProductRepository;
import com.unla.grupo18.services.IProductService;
import com.unla.grupo18.services.IStockService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final IStockService stockService;
    private final ModelMapper modelMapper = new ModelMapper();

    public ProductService(IProductRepository productRepository, IStockService stockService) {
        this.productRepository = productRepository;
        this.stockService = stockService;
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
    public Product findByName(String name) throws Exception {
        return productRepository.findByName(name).orElse(null);
    }


    @Override
    public Product findByCode(String code) throws Exception {
        return productRepository.findByCode(code).orElse(null);
    }

    @Transactional
    public Product save(ProductDtoAdd productDto) throws Exception {

        if (findByName(productDto.getName()) != null) {
            throw new Exception("Product with name " + productDto.getName() + " already exists");
        }


        if (findByCode(productDto.getCode()) != null) {
            throw new Exception("Product with code " + productDto.getCode() + " already exists");
        }


        Product product = modelMapper.map(productDto, Product.class);
        product.setSellPrice(productDto.getCostPrice()*1.5);
        Stock stock = new Stock();
        stock.setCurrentStock(0);
        stock.setCriticStock(productDto.getCriticalStock());
        stock.setProduct(product);


        Stock savedStock = stockService.save(stock);

        product.setStock(savedStock);
        return productRepository.save(product);

    }


    @Override
    public Product update(ProductDtoAdd productDto) throws Exception {

        Product productToUpdate = productRepository.findById(productDto.getId()).orElseThrow(() -> new Exception("Product not found"));

        productToUpdate.setName(productDto.getName());
        productToUpdate.setDescription(productDto.getDescription());
        productToUpdate.setCode(productDto.getCode());
        productToUpdate.setCostPrice(productDto.getCostPrice());

        return productToUpdate = productRepository.save(productToUpdate);

       }

    @Override
    public boolean remove(Long id) throws Exception {


        if (!productRepository.existsById(id)) {
            throw new Exception("Product with id " + id + " not found");
        }
        productRepository.deleteById(id);
        return true;
    }
}
