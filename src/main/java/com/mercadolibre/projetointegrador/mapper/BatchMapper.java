package com.mercadolibre.projetointegrador.mapper;

import com.mercadolibre.projetointegrador.dtos.BatchDTO;
import com.mercadolibre.projetointegrador.dtos.response.BatchDueDateResponseDTO;
import com.mercadolibre.projetointegrador.model.Batch;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BatchMapper {
    private final ModelMapper modelMapper;

    public List<BatchDTO> mapListDtoToEntity(List<Batch> batches){
        return  batches
                .stream()
                .map(batch -> modelMapper.map(batch, BatchDTO.class))
                .collect(Collectors.toList());
    }

    public List<BatchDueDateResponseDTO> mapListDtoReponseToEntity(List<Batch> batches){
        return  batches
                .stream()
                .map(batch -> modelMapper.map(batch, BatchDueDateResponseDTO.class))
                .collect(Collectors.toList());
    }
}
