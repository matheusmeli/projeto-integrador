package com.mercadolibre.projetointegrador.controller;


import com.mercadolibre.projetointegrador.model.Buyer;
import com.mercadolibre.projetointegrador.service.crud.impl.BuyerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@ApiIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/fresh-products/buyers")
public class BuyerController {

    private final BuyerServiceImpl buyerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Buyer create(
            //TODO validate buyer or supervisor
            @Valid @RequestBody Buyer buyer){
        return buyerService.create(buyer);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Buyer update(
            //TODO validate buyer or supervisor
            @Valid @RequestBody Buyer buyer){
        return buyerService.update(buyer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(
            //TODO validate buyer or supervisor
            @Valid @PathVariable Long id){
        buyerService.delete(id);
        return "Buyer of Id " + id + " deleted.";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Buyer> findAllBuyers(){
        return buyerService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Buyer findById(
            //TODO validate buyer or supervisor
            @PathVariable Long id){
        return buyerService.findById(id);
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Buyer findByName(
            //TODO validate buyer or supervisor
            @PathVariable String name){
        return buyerService.findByName(name);
    }

}
