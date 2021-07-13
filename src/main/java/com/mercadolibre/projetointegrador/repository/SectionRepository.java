package com.mercadolibre.projetointegrador.repository;

import com.mercadolibre.projetointegrador.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    Optional<Section> findSectionBySectionCodeAndWarehouse_Id(String sectionCode, Long id);

    List<Section> findSectionsBySectionCode(String sectionCode);
}
