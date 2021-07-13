package com.mercadolibre.projetointegrador.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mercadolibre.projetointegrador.dtos.SectionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSectionResponseDTO {

    private SectionDTO section;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String productId;

    private List<SimpleBatchResponseDTO> batchStock = new ArrayList<>();

}
