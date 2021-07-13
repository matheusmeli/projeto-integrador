package com.mercadolibre.projetointegrador.config;

import com.mercadolibre.projetointegrador.dtos.BatchDTO;
import com.mercadolibre.projetointegrador.dtos.response.BatchDueDateResponseDTO;
import com.mercadolibre.projetointegrador.model.Batch;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ModelMapperConfig {

    @Bean
    @Primary
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE)
                .setSkipNullEnabled(true);

        batchMap(modelMapper);

        return modelMapper;
    }

    private void batchMap(ModelMapper mapper) {
        PropertyMap<Batch, BatchDTO> batchMapToDto = new PropertyMap<Batch, BatchDTO>() {
            protected void configure() {
                map().setProductId(source.getProduct().getId());
                map().setBatchNumber(source.getId());
            }
        };

        PropertyMap<BatchDTO, Batch> batchDtoMapToEntity = new PropertyMap<BatchDTO, Batch>() {
            protected void configure() {
                map().setId(source.getBatchNumber());
            }
        };

        PropertyMap<Batch, BatchDueDateResponseDTO> batchDueDtoMapToEntity = new PropertyMap<Batch, BatchDueDateResponseDTO>() {
            protected void configure() {
                map().setBatchNumber(source.getId());
                map().setProductId(source.getProduct().getId());
                map().setQuantity(source.getCurrentQuantity());
            }
        };

        mapper.addMappings(batchMapToDto);
        mapper.addMappings(batchDtoMapToEntity);
        mapper.addMappings(batchDueDtoMapToEntity);
    }


}
