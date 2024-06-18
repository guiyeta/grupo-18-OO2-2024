package com.unla.grupo18.services.implementation;


import com.unla.grupo18.dto.StockDto;

import com.unla.grupo18.entities.Stock;

import com.unla.grupo18.repositories.IStockRepository;
import com.unla.grupo18.entities.Product;
import com.unla.grupo18.services.IStockService;
import jakarta.transaction.Transactional;
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
    public List<StockDto> findAll() {

        List<Stock> stocks = stockRepository.findAll();
        return stocks
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public  List<StockDto> findStocksWithActiveProduct(){
        List<Stock> stocks = stockRepository.findStocksWithActiveProduct();
        return stocks
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    private StockDto convertToDto(Stock stock) {
        StockDto stockDto = modelMapper.map(stock, StockDto.class);

        Product product = stock.getProduct();
        if (product != null) {
            stockDto.setProductName(product.getName());
        }

        return stockDto;
    }


    @Override
    public Stock save(Stock stock) {
        return stockRepository.save(stock);
    }


    @Override
    public Stock update(StockDto stockDto) throws Exception {
        Stock stock = stockRepository.findById(stockDto.getId())
                .orElseThrow(() -> new Exception("Stock not found"));

        stock.setCriticStock(stockDto.getCriticStock());

        return stockRepository.save(stock);
    }

    public Stock findById(Long id) {
        return stockRepository.findById(id).orElse(null);
    }



/*
    private StockDto mapToStockDto(Stock stock) {
        StockDto stockDto = new StockDto();
        stockDto.setId(stock.getId());
        stockDto.setProductName(stock.getProduct().getName());
        stockDto.setCurrentStock(stock.getCurrentStock());
        stockDto.setCriticStock(stock.getCriticStock());
        return stockDto;
    }
*/




}
