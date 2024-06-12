package com.unla.grupo18.services;

import com.unla.grupo18.dto.PurchaseOrderDto;
import com.unla.grupo18.entities.PurchaseOrder;

public interface IPurchaseOrderService {
    public PurchaseOrder findById(Long id) throws Exception;
    public PurchaseOrderDto save(PurchaseOrder purchaseOrder);
}
