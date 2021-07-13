package com.mercadolibre.projetointegrador.controller;


import com.mercadolibre.projetointegrador.dtos.response.BatchDueDateResponseDTO;
import com.mercadolibre.projetointegrador.service.crud.impl.BatchServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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


}
