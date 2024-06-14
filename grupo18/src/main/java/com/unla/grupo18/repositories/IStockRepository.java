package com.unla.grupo18.repositories;


import com.unla.grupo18.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface IStockRepository extends JpaRepository<Stock, Serializable> {

    public abstract Optional<Stock> findById(Long id);
   
}
