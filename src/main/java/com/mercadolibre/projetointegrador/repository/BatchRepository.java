package com.mercadolibre.projetointegrador.repository;

import com.mercadolibre.projetointegrador.model.Batch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {
    Optional<List<Batch>> findBatchByDueDateBetween(LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT\n" +
            "    C.*\n" +
            "FROM inbound_order A\n" +
            "         INNER JOIN inbound_order_batch_stock B ON A.id = B.inbound_order_id\n" +
            "         INNER JOIN batch C ON C.id = B.batch_stock_id\n" +
            "         INNER JOIN section D ON D.id = A.section_id\n" +
            "         INNER JOIN product E ON E.id = C.product_id\n" +
            "WHERE D.section_code = ?1 AND C.due_date BETWEEN ?2 AND ?3 \n-- #pageable\n",nativeQuery = true)
    Page<Batch> findDueDateBySectionOrdered(String category,
                                                  LocalDate startDate,
                                                  LocalDate endDate,
                                                  Pageable pageable);

}
