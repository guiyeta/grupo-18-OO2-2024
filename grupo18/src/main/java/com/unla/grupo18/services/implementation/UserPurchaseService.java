package com.unla.grupo18.services.implementation;

import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.dto.PurchaseOrderDto;
import com.unla.grupo18.dto.UserPurchaseDto;
import com.unla.grupo18.dto.UserPurchaseDtoAdd;
import com.unla.grupo18.entities.*;
import com.unla.grupo18.repositories.IUserPurchaseRepository;
import com.unla.grupo18.services.IProductService;
import com.unla.grupo18.services.IPurchaseOrderService;
import com.unla.grupo18.services.IUserPurchaseService;
import jakarta.transaction.Transactional;
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
    private final IPurchaseOrderService purchaseOrderService;

    public UserPurchaseService(IUserPurchaseRepository userPurchaseRepository, UserService userService, IProductService productService, IPurchaseOrderService purchaseOrderService) {
        this.userPurchaseRepository = userPurchaseRepository;
        this.userService = userService;
        this.productService = productService;
        this.purchaseOrderService = purchaseOrderService;
    }

    @Override
    public List<UserPurchaseDto> findAll(){
        List<UserPurchase> userPurchases = userPurchaseRepository.findAll();
        return userPurchases
                .stream()
                .map(userPurchase -> modelMapper.map(userPurchase, UserPurchaseDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserPurchase save(UserPurchaseDtoAdd userPurchaseDto) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Product product = productService.findByName(userPurchaseDto.getProductName());
        if (product == null) {
            throw new Exception("Product not found: " + userPurchaseDto.getProductName());
        }

        int newStock = product.getStock().getCurrentStock() - userPurchaseDto.getAmount();
        if (newStock < 0) {
            throw new Exception("There is not enough stock to make the purchase.");
        }
        product.getStock().setCurrentStock(newStock);

        if (newStock< product.getStock().getCriticStock()){

            int productsAlreadyOrdered = getTotalAmountByProductId(product);

            PurchaseOrderDto purchaseOrder = new PurchaseOrderDto();
            int amountToRestock = product.getStock().getCriticStock() - newStock - productsAlreadyOrdered;
            purchaseOrder.setAmount(amountToRestock);
            purchaseOrder.setOrderDate(LocalDate.now());
           // purchaseOrder.setStatus("PENDING-AUTOMATIC PURCHASE TO RESTOCK");
            purchaseOrder.setProductName(product.getName());
            purchaseOrder.setSupplier("GENERIC SUPPLIER");
          //  purchaseOrder.setTotalPrice(amountToRestock * product.getCostPrice() );

            purchaseOrderService.save(purchaseOrder);
        }

        UserPurchase userPurchase = new UserPurchase();
        userPurchase.setProduct(product);
        userPurchase.setPurchaseDate(LocalDate.now());
        userPurchase.setAmount(userPurchaseDto.getAmount());
        User user = userService.findByUsername(username);
        userPurchase.setUser(user);
        userPurchase.setTotalPrice(userPurchaseDto.getAmount() * product.getSellPrice());

        return userPurchaseRepository.save(userPurchase);

    }

    private int getTotalAmountByProductId(Product product) {

        return purchaseOrderService.findByProductIdAndStatusPending(product.getId()).stream()
                .mapToInt(PurchaseOrder::getAmount)
                .sum();
    }




}
