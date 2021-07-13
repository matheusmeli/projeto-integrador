package com.mercadolibre.projetointegrador.controller;

import com.mercadolibre.projetointegrador.dtos.PurchaseOrderDTO;
import com.mercadolibre.projetointegrador.model.Product;
import com.mercadolibre.projetointegrador.service.crud.impl.PurchaseOrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Api(tags = "Fresh products", value = "/api/v1/fresh-products")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/fresh-products")
public class PurchaseOrderController {

    private final PurchaseOrderServiceImpl purchaseOrderService;

    @ApiOperation("US02 - Get Products from Order")
    @GetMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProductsFromOrder(@PathVariable Long id){
        return purchaseOrderService.getProducts(id);
    }

    @ApiOperation("US02 - Create Purchase Order")
    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public double insertPurchaseOder(@Valid @RequestBody PurchaseOrderDTO purchaseOrderDTO){
        return purchaseOrderService.insertAndCalculatePurchaseOrder(purchaseOrderDTO);
    }

    @ApiOperation("US02 - Get Products from Category")
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public Set<Product> getProductsFromCategory(@RequestParam(value = "category", defaultValue = "FS") String category){
        return purchaseOrderService.getSectorProducts(category);
    }

    @ApiOperation("US02 - Update Purchase Order")
    @PutMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public double updatePurchaseOder(
            @Valid @RequestBody PurchaseOrderDTO purchaseOrderDTO,
            @PathVariable Long id){
        return purchaseOrderService.updatePurchaseOrder(purchaseOrderDTO, id);
    }
}
