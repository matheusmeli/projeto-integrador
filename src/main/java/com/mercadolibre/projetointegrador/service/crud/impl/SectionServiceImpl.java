package com.mercadolibre.projetointegrador.service.crud.impl;

import com.mercadolibre.projetointegrador.dtos.SectionDTO;
import com.mercadolibre.projetointegrador.exceptions.NotFoundException;
import com.mercadolibre.projetointegrador.model.Section;
import com.mercadolibre.projetointegrador.repository.SectionRepository;
import com.mercadolibre.projetointegrador.service.crud.ICRUD;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SectionServiceImpl implements ICRUD<Section> {

    private final SectionRepository sectionRepository;

    public Section findSectionBySectionCodeAndWarehouseId(SectionDTO sectionDTO) {
        return sectionRepository.findSectionBySectionCodeAndWarehouse_Id(
                sectionDTO.getId(),
                sectionDTO.getWarehouseCode())
                .orElseThrow(() -> new NotFoundException(
                        "Seção de id: " + sectionDTO.getId() + " e/ou Warehouse " + sectionDTO.getWarehouseCode() + " não encontrado"));
    }

    public List<Section> findSectionsBySectionCode(String sectionCode){
        return sectionRepository.findSectionsBySectionCode(sectionCode);
    }

    @Override
    public Section create(Section section) {
        return null;
    }

    @Override
    public Section update(Section section) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Section findById(Long id) {
        return null;
    }

    @Override
    public List<Section> findAll() {
        return null;
    }
}
