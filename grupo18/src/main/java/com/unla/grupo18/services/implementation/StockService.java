package com.unla.grupo18.services.implementation;

import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.dto.StockDto;
import com.unla.grupo18.entities.Product;
import com.unla.grupo18.entities.Stock;
import com.unla.grupo18.repositories.IProductRepository;
import com.unla.grupo18.repositories.IStockRepository;
import com.unla.grupo18.services.IStockService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class StockService implements IStockService {

    private final IStockRepository stockRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public StockService(IStockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public List<StockDto> getAll() {
        List<Stock> stocks = stockRepository.findAll();
        return stocks
                .stream()
                .map(stock -> modelMapper.map(stock, StockDto.class))
                .collect(Collectors.toList());
    }
}
