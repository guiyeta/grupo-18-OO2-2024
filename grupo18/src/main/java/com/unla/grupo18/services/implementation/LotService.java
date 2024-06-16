package com.unla.grupo18.services.implementation;


import com.unla.grupo18.dto.LotDto;
import com.unla.grupo18.dto.LotDtoAdd;
import com.unla.grupo18.entities.Lot;
import com.unla.grupo18.entities.PurchaseOrder;
import com.unla.grupo18.entities.Stock;
import com.unla.grupo18.repositories.ILotRepository;
import com.unla.grupo18.services.ILotService;
import com.unla.grupo18.services.IPurchaseOrderService;
import com.unla.grupo18.services.IStockService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LotService implements ILotService {

    private final ILotRepository lotRepository;
    private final IPurchaseOrderService purchaseOrderService;
    private final ModelMapper modelMapper = new ModelMapper();
    private final IStockService stockService;


    public LotService(ILotRepository lotRepository, IPurchaseOrderService purchaseOrderService, IStockService stockService) {
        this.lotRepository = lotRepository;
        this.purchaseOrderService = purchaseOrderService;
        this.stockService = stockService;
    }

    @Override
    public List<LotDto> findAll() {
        List<Lot> lots = lotRepository.findAll();
        return lots
                .stream()
                .map(stock -> modelMapper.map(stock, LotDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public Lot save(LotDtoAdd lotDtoAdd) throws Exception{

        PurchaseOrder purchaseOrder= null;
        try {
            purchaseOrder = purchaseOrderService.findById(lotDtoAdd.getPurchaseOrderId());

            if (lotDtoAdd.getReceptionDate().isBefore(purchaseOrder.getOrderDate())) {
                throw new Exception("Reception date cannot be before order date. ("+ purchaseOrder.getOrderDate() +")");
            }
            purchaseOrder.setStatus("Received");

            Stock stock = purchaseOrder.getProduct().getStock();
            if (stock == null) {
                throw new Exception("Stock not found for the product");
            }
            stock.updateStock(purchaseOrder.getAmount());

            stockService.save(stock);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        Lot lot = new Lot();
        lot.setPurchaseOrder(purchaseOrder);
        lot.setReceptionDate(lotDtoAdd.getReceptionDate());

        return lotRepository.save(lot);
    }
}
