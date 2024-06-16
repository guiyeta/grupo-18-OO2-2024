package com.unla.grupo18.services;

import com.unla.grupo18.dto.PurchaseOrderDto;
import com.unla.grupo18.entities.PurchaseOrder;

import java.util.List;

public interface IPurchaseOrderService {
    public PurchaseOrder save(PurchaseOrderDto purchaseOrderDto) throws Exception;
    public List<PurchaseOrderDto> findAll();
    public PurchaseOrder findById(Long id) throws Exception;
}
