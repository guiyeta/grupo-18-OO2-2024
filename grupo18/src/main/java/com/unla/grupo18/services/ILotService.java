package com.unla.grupo18.services;

import com.unla.grupo18.dto.LotDto;
import com.unla.grupo18.dto.LotDtoAdd;
import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.entities.Lot;
import com.unla.grupo18.entities.Product;
import com.unla.grupo18.exceptions.ProductNotFoundException;

import java.util.List;

public interface ILotService {

    public List<LotDto> findAll();
    public Lot save(LotDtoAdd lotDtoAdd) throws Exception;
}
