package com.unla.grupo18.services;

import com.unla.grupo18.dto.StockDto;
import com.unla.grupo18.dto.UserPurchaseDto;
import com.unla.grupo18.dto.UserPurchaseDtoAdd;
import com.unla.grupo18.entities.UserPurchase;

import java.util.List;

public interface IUserPurchaseService {

    List<UserPurchaseDto> findAll();
    public UserPurchase save(UserPurchaseDtoAdd userPurchaseDto) throws Exception;
}
