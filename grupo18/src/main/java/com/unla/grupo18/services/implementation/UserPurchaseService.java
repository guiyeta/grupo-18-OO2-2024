package com.unla.grupo18.services.implementation;

import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.dto.UserPurchaseDto;
import com.unla.grupo18.dto.UserPurchaseDtoAdd;
import com.unla.grupo18.entities.*;
import com.unla.grupo18.repositories.IUserPurchaseRepository;
import com.unla.grupo18.services.IProductService;
import com.unla.grupo18.services.IUserPurchaseService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPurchaseService implements IUserPurchaseService {

    private final IUserPurchaseRepository userPurchaseRepository;
    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();
    private final IProductService productService;

    public UserPurchaseService(IUserPurchaseRepository userPurchaseRepository, UserService userService, IProductService productService) {
        this.userPurchaseRepository = userPurchaseRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public List<UserPurchaseDto> findAll(){
        List<UserPurchase> userPurchases = userPurchaseRepository.findAll();
        return userPurchases
                .stream()
                .map(userPurchase -> modelMapper.map(userPurchase, UserPurchaseDto.class))
                .collect(Collectors.toList());
    }

    public UserPurchase save(UserPurchaseDtoAdd userPurchaseDto) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Obtener el username

        Product product = null;
        try {
            product = productService.findByName(userPurchaseDto.getProductName());
            //TODO: NO PUEDE SER MENOR A 0. VALIDAR
            product.getStock().setCurrentStock(product.getStock().getCurrentStock() - userPurchaseDto.getAmount());

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        UserPurchase userPurchase = new UserPurchase();
        userPurchase.setProduct(product);
        userPurchase.setPurchaseDate(LocalDate.now());
        userPurchase.setAmount(userPurchaseDto.getAmount());
        User user = userService.findByUsername(username);
        userPurchase.setUser(user);
        userPurchase.setTotalPrice(userPurchaseDto.getAmount() * product.getCostPrice());

        return userPurchaseRepository.save(userPurchase);

    }

}
