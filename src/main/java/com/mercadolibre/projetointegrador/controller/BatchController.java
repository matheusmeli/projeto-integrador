package com.mercadolibre.projetointegrador.controller;


import com.mercadolibre.projetointegrador.dtos.response.BatchDueDateResponseDTO;
import com.mercadolibre.projetointegrador.service.crud.impl.BatchServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.mercadolibre.projetointegrador.dtos.response.ProductSectionResponseDTO;
import com.mercadolibre.projetointegrador.dtos.response.WarehouseStockResponseDTO;
import com.mercadolibre.projetointegrador.model.InboundOrder;
import com.mercadolibre.projetointegrador.service.crud.impl.BatchServiceImpl;
import com.mercadolibre.projetointegrador.service.impl.SessionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Fresh products DueDate", value = "/api/v1/fresh-products/duedate")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/fresh-products/duedate")
public class BatchController {

    private final BatchServiceImpl batchService;

    @ApiOperation("US05 - Check Batch Stock Due Date")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BatchDueDateResponseDTO> findByDueDate(@RequestParam int days){
        return batchService.findByDueDateBetween(days);
    }

    @ApiOperation("US05 - Check Batch Stock Due Date by Category")
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<BatchDueDateResponseDTO> findByDueDateList(@RequestParam int days,
                                                           @RequestParam(defaultValue = "FS") String category,
                                                           @RequestParam(defaultValue = "asc") String order) {
        return batchService.findByDueDateCategoryBetweenDates(category, days, order);
    }

    @GetMapping("/sections")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductSectionResponseDTO> findExpiringProductByWarehouse(@RequestParam int days,
                                                             @RequestHeader("Authorization") String token){
        return batchService.findExpiringProductByWarehouse(days, SessionServiceImpl.getUsername(token));
    }



}
