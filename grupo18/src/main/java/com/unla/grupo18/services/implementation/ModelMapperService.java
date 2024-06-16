package com.unla.grupo18.services.implementation;


import com.unla.grupo18.dto.PurchaseOrderDto;
import com.unla.grupo18.entities.Product;
import com.unla.grupo18.entities.PurchaseOrder;
import com.unla.grupo18.services.IProductService;
import org.modelmapper.ModelMapper;

public class ModelMapperService {

    //No se si esta clase sirve?
    private final ModelMapper modelMapper = new ModelMapper();

    private final IProductService productService;

    public ModelMapperService(IProductService productService) {
        this.productService = productService;
    }

    public PurchaseOrder convertDtoToEntity(PurchaseOrderDto purchaseOrderDto) throws Exception {
        PurchaseOrder purchaseOrder = modelMapper.map(purchaseOrderDto, PurchaseOrder.class);

        Product product = productService.findByName(purchaseOrderDto.getProductName());

        purchaseOrder.setProduct(product);

        return purchaseOrder;
    }

}
