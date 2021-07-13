package com.mercadolibre.projetointegrador.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.projetointegrador.model.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleBatchResponseDTO {

    @JsonProperty("batchNumber")
    private Long id;

    private int currentQuantity;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private String dueDate;

    @JsonIgnore
    private Section section;
}
