package com.unla.grupo18.services;

import com.unla.grupo18.dto.PurchaseProductDto;
import com.unla.grupo18.entities.PurchaseProduct;

public interface IPurchaseProductService {
	
	public PurchaseProduct findById(Long id);
}
