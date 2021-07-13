package com.mercadolibre.projetointegrador.controller;

import com.mercadolibre.projetointegrador.model.Batch;
import com.mercadolibre.projetointegrador.service.impl.ExpiredBatchServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class ExpiredBatchController {

    private final ExpiredBatchServiceImpl expiredBatchService;

    public ExpiredBatchController(ExpiredBatchServiceImpl expiredBatchService) {
        this.expiredBatchService = expiredBatchService;
    }

    @ApiOperation("US06 - Check for due Date")
    @GetMapping(name = "/expired")
    @ResponseStatus(HttpStatus.OK)
    public List<Batch> checkForDueDate() {
        return expiredBatchService.removeExpiredBatches();
    }
}
