package com.mercadolibre.projetointegrador.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    private String id;

    private int currentQuantity;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private String dueDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String productId;

    @JsonIgnore
    private Section section;
}
