package com.mercadolibre.projetointegrador.controller;


import com.mercadolibre.projetointegrador.model.Seller;
import com.mercadolibre.projetointegrador.service.crud.impl.SellerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/fresh-products/sellers")
public class SellerController {

    private final SellerServiceImpl sellerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Seller create(
            //TODO validate seller
            @Valid @RequestBody Seller seller){
        return sellerService.create(seller);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Seller update(
            //TODO validate seller or supervisor
            @Valid @RequestBody Seller seller){
        return sellerService.update(seller);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(
            //TODO validate seller or supervisor
            @Valid @PathVariable Long id){
        sellerService.delete(id);
        return "Seller of Id " + id + " deleted.";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Seller> findAllProducts(){
        return sellerService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Seller findById(@PathVariable Long id){
        return sellerService.findById(id);
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Seller findByName(@PathVariable String name){
        return sellerService.findByName(name);
    }

    @GetMapping("/seller/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Seller findAllBySellerName(@PathVariable String name){
        return sellerService.findByName(name);
    }

}
