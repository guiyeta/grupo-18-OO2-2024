package com.unla.grupo18.services;

import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.dto.StockDto;

import java.util.List;

public interface IStockService {

    public List<StockDto> getAll();

}
