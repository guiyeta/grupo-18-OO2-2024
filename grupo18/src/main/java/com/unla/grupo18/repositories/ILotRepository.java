package com.unla.grupo18.repositories;

import com.unla.grupo18.entities.Lot;
import com.unla.grupo18.entities.Product;
import com.unla.grupo18.entities.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface ILotRepository extends JpaRepository<Lot, Serializable> {

    public abstract Optional<Lot> findById(Long id);
    boolean existsByPurchaseOrderId(Long purchaseOrderId);
}
