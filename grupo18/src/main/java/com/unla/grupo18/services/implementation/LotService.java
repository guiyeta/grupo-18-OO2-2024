package com.unla.grupo18.services.implementation;


import com.unla.grupo18.dto.LotDto;
import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.dto.PurchaseOrderDto;
import com.unla.grupo18.entities.Lot;
import com.unla.grupo18.entities.Product;
import com.unla.grupo18.entities.PurchaseOrder;
import com.unla.grupo18.exceptions.ProductNotFoundException;
import com.unla.grupo18.repositories.ILotRepository;
import com.unla.grupo18.repositories.IProductRepository;
import com.unla.grupo18.services.ILotService;
import com.unla.grupo18.services.IProductService;
import com.unla.grupo18.services.IPurchaseOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LotService implements ILotService {

    private final ILotRepository lotRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    private final IPurchaseOrderService purchaseOrderService;
    private final IProductService productService;

    public LotService(ILotRepository lotRepository, IPurchaseOrderService purchaseOrderService, IProductService productService) {
        this.lotRepository = lotRepository;
        this.purchaseOrderService = purchaseOrderService;
        this.productService = productService;
    }

    @Override
    public List<LotDto> getAll() {

        List<Lot> lots = lotRepository.findAll();
        return lots
                .stream()
                .map(lot -> modelMapper.map(lot, LotDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public LotDto findById(Long id) {
          Lot lot = lotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lot not found with id " + id));
        return modelMapper.map(lot,LotDto.class);
    }

    @Override
    public LotDto save(Lot lot) throws Exception {
        try {
            PurchaseOrder purchaseOrder = purchaseOrderService.findById(lot.getPurchaseOrder().getId());
            Product product = productService.findById(lot.getProduct().getId());
            Lot receivedLot = new Lot();

            receivedLot.setReceptionDate(LocalDate.now());
            receivedLot.setSupplier(lot.getSupplier());
            receivedLot.setReceivedAmount(lot.getReceivedAmount());
            receivedLot.setPurchasePrice(lot.getPurchasePrice());
            receivedLot.setProduct(product);
            receivedLot.setPurchaseOrder(purchaseOrder);

            purchaseOrder.setStatus("Completed");
            purchaseOrderService.save(purchaseOrder);

            receivedLot.registerEntry();
            return modelMapper.map(lotRepository.save(receivedLot), LotDto.class);
        }catch (Exception e) {
            String errorMessage = "Error occurred while saving the lot: " + e.getMessage();
            throw new Exception(errorMessage);
        }

    }

    @Override
    public LotDto update(Lot lot) throws Exception {
        return null;
    }

    @Override
    public boolean remove(Long id) throws ProductNotFoundException {
        return false;
    }
}


