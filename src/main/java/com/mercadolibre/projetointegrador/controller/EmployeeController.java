package com.mercadolibre.projetointegrador.controller;


import com.mercadolibre.projetointegrador.model.Employee;
import com.mercadolibre.projetointegrador.service.crud.impl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/fresh-products/employees")
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(
            //TODO validate supervisor or admin
            @Valid @RequestBody Employee employee){
        return employeeService.create(employee);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(
            //TODO validate supervisor
            @Valid @RequestBody Employee employee){
        return employeeService.update(employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(
            //TODO validate supervisor
            @Valid @PathVariable Long id){
        employeeService.delete(id);
        return "Buyer of Id " + id + " deleted.";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> findAllEmployees(){
        return employeeService.findAll();
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee findById(
            //TODO validate buyer or supervisor
            @PathVariable Long id){
        return employeeService.findById(id);
    }

    @GetMapping("/employee/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Employee findByName(
            //TODO validate buyer or supervisor
            @PathVariable String name){
        return null /*employeeService.findByName(name)*/;
    }

}
