package com.unla.grupo18.services.implementation;

import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.dto.PurchaseOrderDto;
import com.unla.grupo18.entities.Product;
import com.unla.grupo18.entities.PurchaseOrder;
import com.unla.grupo18.exceptions.ProductNotFoundException;
import com.unla.grupo18.repositories.IProductRepository;
import com.unla.grupo18.repositories.IPurchaseOrderRepository;
import com.unla.grupo18.services.IProductService;
import com.unla.grupo18.services.IPurchaseOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService implements IPurchaseOrderService {

    private final IPurchaseOrderRepository purchaseOrderRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final IProductService productService;

    public PurchaseOrderService(IPurchaseOrderRepository purchaseOrderRepository, IProductService productService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.productService = productService;
    }

    @Override
    public PurchaseOrder save(PurchaseOrderDto purchaseOrderDto) throws Exception {
        Product product = null;
        try {
            product = productService.findByName(purchaseOrderDto.getProductName());

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setProduct(product);
        purchaseOrder.setAmount(purchaseOrderDto.getAmount());
        purchaseOrder.setOrderDate(purchaseOrderDto.getOrderDate());
        purchaseOrder.setStatus("PENDING");
        purchaseOrder.setSupplier(purchaseOrderDto.getSupplier());
        purchaseOrder.setTotalPrice(
                purchaseOrderDto.getAmount() * purchaseOrder.getProduct().getCostPrice()
        );

        return purchaseOrderRepository.save(purchaseOrder);
    }


    @Override
    public List<PurchaseOrderDto> findAll() {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        return purchaseOrders
                .stream()
                .map(purchaseOrder -> modelMapper.map(purchaseOrder, PurchaseOrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseOrder findById(Long id) throws Exception {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new Exception("Purchase order not found with id " + id));
    }

    @Override
    public List<PurchaseOrder> findByProductIdAndStatusPending(Long productId){
        return purchaseOrderRepository.findByProductIdAndStatusPending(productId);
    }



}
