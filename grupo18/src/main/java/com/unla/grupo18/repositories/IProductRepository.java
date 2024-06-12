package com.unla.grupo18.repositories;

import com.unla.grupo18.entities.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product, Serializable> {

    public abstract Optional<Product> findByCode(String code);
    public abstract Optional<Product> findByName(String name);
    public abstract Optional<Product> findById(Long id);

    @Query("SELECT p FROM Product p WHERE p.price <= :maxPrice")
    List<Product> findByPriceLessThanOrEqual(double maxPrice);



}
