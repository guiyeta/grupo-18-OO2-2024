package com.unla.grupo18.repositories;

import com.unla.grupo18.entities.UserPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface IUserPurchaseRepository extends JpaRepository<UserPurchase, Serializable> {
}
