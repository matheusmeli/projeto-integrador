package com.mercadolibre.projetointegrador.controller;

import com.mercadolibre.projetointegrador.model.Supervisor;
import com.mercadolibre.projetointegrador.service.crud.impl.SupervisorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/fresh-products/supervisors")
public class SupervisorController {

    private final SupervisorServiceImpl supervisorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Supervisor create(
            //TODO validate supervisor or admin
            @Valid @RequestBody Supervisor supervisor){
        return supervisorService.create(supervisor);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Supervisor update(
            //TODO validate supervisor
            @Valid @RequestBody Supervisor supervisor){
        return supervisorService.update(supervisor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(
            //TODO validate supervisor
            @Valid @PathVariable Long id){
        supervisorService.delete(id);
        return "Buyer of Id " + id + " deleted.";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Supervisor> findAllSupervisors(){
        return supervisorService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Supervisor findById(
            //TODO validate buyer or supervisor
            @PathVariable Long id){
        return supervisorService.findById(id);
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Supervisor findByName(
            //TODO validate buyer or supervisor
            @PathVariable String name){
        return supervisorService.findByName(name);
    }

}
