package com.mercadolibre.projetointegrador.controller;

import com.mercadolibre.projetointegrador.dtos.BatchDTO;
import com.mercadolibre.projetointegrador.dtos.InboundOrderDTO;
import com.mercadolibre.projetointegrador.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.projetointegrador.service.crud.impl.InboundOrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.mercadolibre.projetointegrador.service.impl.SessionServiceImpl.getUsername;

@Api(tags = "Inbound Order", value = "/api/v1/fresh-products/inboundorder")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/fresh-products/inboundorder")
public class InboundOrderController {

    private final InboundOrderServiceImpl inboundOrderService;

    @ApiOperation("US01 - Create Inbound Order")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InboundOrderResponseDTO create(
            @Valid @RequestBody InboundOrderDTO inboundOrderDTO,
            @RequestHeader(value="Authorization") String token){
        return inboundOrderService.create(inboundOrderDTO, getUsername(token));
    }

    @ApiOperation("US01 - Update Inbound Order")
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<BatchDTO> update(
            @Valid @RequestBody InboundOrderDTO inboundOrderDTO,
            @PathVariable("id") Long idInboundOrder,
            @RequestHeader(value="Authorization") String token){
        return inboundOrderService.update(inboundOrderDTO, idInboundOrder, getUsername(token));
    }

}
