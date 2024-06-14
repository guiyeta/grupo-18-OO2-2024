package com.unla.grupo18.services;


import com.unla.grupo18.dto.StockDto;
import com.unla.grupo18.entities.Product;
import com.unla.grupo18.entities.Stock;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IStockService {

    public List<StockDto> getAll();
    
    

}
