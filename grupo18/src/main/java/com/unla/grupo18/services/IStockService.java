package com.unla.grupo18.services;

import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.dto.StockDto;
import com.unla.grupo18.entities.Stock;

import java.util.List;

public interface IStockService {

    List<StockDto> findAll();
    Stock save(Stock stock);
    List<StockDto> findStocksWithActiveProduct();
    public Stock update(StockDto stockDto) throws Exception;
    public Stock findById(Long id);






}
