package com.unla.grupo18.services.implementation;



import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.dto.ProductDtoAdd;
import com.unla.grupo18.dto.ProductDtoUpdate;
import com.unla.grupo18.entities.Product;
import com.unla.grupo18.entities.Stock;
import com.unla.grupo18.repositories.IProductRepository;
import com.unla.grupo18.services.IProductService;
import com.unla.grupo18.services.IStockService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product findByName(String name) {
        return productRepository.findByName(name).orElse(null);
    }

    @Override
    public Product findByCode(String code) {
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
        product.setActive(true);
        Stock stock = new Stock();
        stock.setCurrentStock(0);
        stock.setCriticStock(productDto.getCriticalStock());
        stock.setProduct(product);
        Stock savedStock = stockService.save(stock);
        product.setStock(savedStock);
        return productRepository.save(product);

    }

    /*
    @Override
    public Product update(ProductDtoAdd productDto) throws Exception {

        Product productToUpdate = productRepository.findById(productDto.getId()).orElseThrow(() -> new Exception("Product not found"));

        if (findByName(productDto.getName()) != null) {
            throw new Exception("Product with name " + productDto.getName() + " already exists");
        }

        if (findByCode(productDto.getCode()) != null) {
            throw new Exception("Product with code " + productDto.getCode() + " already exists");
        }

        productToUpdate.setName(productDto.getName());
        productToUpdate.setDescription(productDto.getDescription());
        productToUpdate.setCode(productDto.getCode());
        productToUpdate.setCostPrice(productDto.getCostPrice());
        productToUpdate.setSellPrice(productDto.getCostPrice()* 1.5);
        return productRepository.save(productToUpdate);

       }

     */

    @Override
    public boolean remove(Long id) throws Exception {

        if (!productRepository.existsById(id)) {
            throw new Exception("Product with id " + id + " not found");
        }
        productRepository.deleteById(id);
        return true;
    }


    public List<ProductDto> getActiveProducts() {

        List<Product> activeProducts = productRepository.findByActiveTrue();
        return activeProducts
                .stream()
                .map(activeProduct -> modelMapper.map(activeProduct, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getInactiveProducts() {
        List<Product> inactiveProducts = productRepository.findByActiveFalse();
        return inactiveProducts
                .stream()
                .map(inactiveProduct -> modelMapper.map(inactiveProduct, ProductDto.class))
                .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public void deactivateProduct(Long productId) throws Exception {
        Product product = findById(productId);
        product.setActive(false);
        productRepository.save(product);
    }


    @Transactional
    @Override
    public void activateProduct(Long productId) throws Exception {
        Product product = findById(productId);
        product.setActive(true);
        productRepository.save(product);
    }


    public ProductDtoUpdate getProductById(Long id) {


        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product p = product.get();
            ProductDtoUpdate productDTO = new ProductDtoUpdate();
            productDTO.setId(p.getId());
            productDTO.setCode(p.getCode());
            productDTO.setDescription(p.getDescription());
            productDTO.setName(p.getName());
            productDTO.setCostPrice(p.getCostPrice());
            return productDTO;
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    @Override
    public Product update(ProductDtoUpdate productDto) throws Exception {

        Product productToUpdate = productRepository.findById(productDto.getId()).orElseThrow(() -> new Exception("Product not found"));

        if (!productToUpdate.getName().equals(productDto.getName())) {
            Product existingProductWithName = findByName(productDto.getName());
            if (existingProductWithName != null && !existingProductWithName.getId().equals(productToUpdate.getId())) {
                throw new Exception("Product with name " + productDto.getName() + " already exists");
            }
        }

        if (!productToUpdate.getCode().equals(productDto.getCode())) {
            Product existingProductWithCode = findByCode(productDto.getCode());
            if (existingProductWithCode != null && !existingProductWithCode.getId().equals(productToUpdate.getId())) {
                throw new Exception("Product with code " + productDto.getCode() + " already exists");
            }
        }


        productToUpdate.setName(productDto.getName());
        productToUpdate.setDescription(productDto.getDescription());
        productToUpdate.setCode(productDto.getCode());
        productToUpdate.setCostPrice(productDto.getCostPrice());
        productToUpdate.setSellPrice(productDto.getCostPrice()* 1.5);

        /*
         if (!product.getName().equals(productDto.getName()) && findByName(productDto.getName()) != null) {
            throw new Exception("Product with name " + productDto.getName() + " already exists");
         }

         if (!product.getCode().equals(productDto.getCode()) && findByCode(productDto.getCode()) != null) {
            throw new Exception("Product with code " + productDto.getCode() + " already exists");
         }

        */


        return productRepository.save(productToUpdate);
        }

}
