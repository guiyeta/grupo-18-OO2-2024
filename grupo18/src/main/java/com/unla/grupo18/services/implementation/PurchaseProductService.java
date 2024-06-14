package com.unla.grupo18.services.implementation;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.grupo18.dto.PurchaseProductDto;
import com.unla.grupo18.entities.Product;
import com.unla.grupo18.entities.PurchaseProduct;
import com.unla.grupo18.entities.Stock;
import com.unla.grupo18.repositories.IProductRepository;
import com.unla.grupo18.repositories.IPurchaseProductRepository;
import com.unla.grupo18.repositories.IStockRepository;
import com.unla.grupo18.services.IPurchaseProductService;

@Service
public class PurchaseProductService implements IPurchaseProductService{
	
	private final IPurchaseProductRepository purchaseProduct;
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IStockRepository stockRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    
    public	PurchaseProductService (IPurchaseProductRepository purchaseProduct) {
        this.purchaseProduct = purchaseProduct;
    }
    public PurchaseProduct findById(Long id) {
    	return purchaseProduct.findById(id).orElse(null);
    }
    public void simulatePurchase(PurchaseProduct purchaseProduct) {
        Product product = productRepository.findById(purchaseProduct.getProduct().getId()).orElseThrow();
        Stock stock = product.getStock();

        int newStockQuantity = stock.getCurrentStock() - purchaseProduct.getAmount();

        if (newStockQuantity >= 0) {
            stock.setCurrentStock(newStockQuantity);
            stockRepository.save(stock);
            System.out.println("Purchase successful! Stock updated.");
        } else {
            System.out.println("Insufficient stock. Purchase failed.");
        }
    }
   
}


