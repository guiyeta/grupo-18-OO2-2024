package com.unla.grupo18.repositories;

import com.unla.grupo18.entities.Lot;
import com.unla.grupo18.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface IStockRepository extends JpaRepository<Stock, Serializable> {

    @Query("SELECT s FROM Stock s WHERE s.product.active = true")
    public abstract List<Stock> findStocksWithActiveProduct();



}
