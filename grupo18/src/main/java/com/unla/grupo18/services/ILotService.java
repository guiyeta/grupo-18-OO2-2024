package com.unla.grupo18.services;

import com.unla.grupo18.dto.LotDto;
import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.entities.Lot;
import com.unla.grupo18.entities.Product;
import com.unla.grupo18.exceptions.ProductNotFoundException;

import java.util.List;

public interface ILotService {

    public List<LotDto> getAll();

    public LotDto findById(Long id) throws Exception;

    public LotDto save(Lot lot) throws Exception;

    public LotDto update(Lot lot) throws Exception;

    public boolean remove(Long id) throws Exception;
}
