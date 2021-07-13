package com.mercadolibre.projetointegrador.dtos.response;

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

    private Long productId;

    private List<SimpleBatchResponseDTO> batchStock = new ArrayList<>();

}
