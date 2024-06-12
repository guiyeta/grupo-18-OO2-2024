package com.unla.grupo18.services.implementation;

import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.dto.PurchaseOrderDto;
import com.unla.grupo18.entities.Product;
import com.unla.grupo18.entities.PurchaseOrder;
import com.unla.grupo18.exceptions.ProductNotFoundException;
import com.unla.grupo18.repositories.IProductRepository;
import com.unla.grupo18.repositories.IPurchaseOrderRepository;
import com.unla.grupo18.services.IPurchaseOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderService implements IPurchaseOrderService {

    private final IPurchaseOrderRepository purchaseOrderRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public PurchaseOrderService(IPurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Override
    public PurchaseOrder findById(Long id) throws Exception {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new Exception("Purchase Order not found with id " + id));

    }

    @Override
    public PurchaseOrderDto save(PurchaseOrder purchaseOrder){
        return modelMapper.map(purchaseOrderRepository.save(purchaseOrder),PurchaseOrderDto.class);
    }

}
