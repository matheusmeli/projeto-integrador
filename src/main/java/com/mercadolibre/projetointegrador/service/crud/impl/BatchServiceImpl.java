package com.mercadolibre.projetointegrador.service.crud.impl;

import com.mercadolibre.projetointegrador.dtos.BatchDTO;
import com.mercadolibre.projetointegrador.dtos.ProductDTO;
import com.mercadolibre.projetointegrador.dtos.PurchaseOrderDTO;
import com.mercadolibre.projetointegrador.dtos.response.BatchDueDateResponseDTO;
import com.mercadolibre.projetointegrador.exceptions.NotFoundException;
import com.mercadolibre.projetointegrador.mapper.BatchMapper;
import com.mercadolibre.projetointegrador.model.Batch;
import com.mercadolibre.projetointegrador.model.Product;
import com.mercadolibre.projetointegrador.model.PurchaseProduct;
import com.mercadolibre.projetointegrador.repository.BatchRepository;
import com.mercadolibre.projetointegrador.service.crud.ICRUD;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BatchServiceImpl implements ICRUD<Batch> {

    private final ModelMapper modelMapper;
    private final ProductServiceImpl productService;
    private final BatchRepository batchRepository;

    private final BatchMapper batchMapper;

    public List<Batch> create(List<BatchDTO> batchDTOS) {

        List<Batch> batches = new ArrayList<>();

        for (BatchDTO batch : batchDTOS) {
            Product product = productService.findById(batch.getProductId());
            Batch batchItem = modelMapper.map(batch, Batch.class);
            batchItem.setProduct(product);
            batches.add(batchItem);
        }

        return batches;
    }

    public List<Batch> update(List<BatchDTO> batchDTOS) {

        List<Batch> batches = new ArrayList<>();

        for (BatchDTO batch : batchDTOS) {
            Product product = productService.findById(batch.getProductId());
            Batch foundBatch = findById(batch.getBatchNumber());
            batch.setBatchNumber(foundBatch.getId());
            Batch batchItem = modelMapper.map(batch, Batch.class);
            batchItem.setProduct(product);
            batches.add(batchItem);
        }

        return batches;
    }

    public Batch findBatchContainingValidProduct(Long id, int quantity){
        return batchRepository.findAll().stream()
                .filter(batch -> batch.getProduct().getId().equals(id))
                .filter(batch -> batch.getCurrentQuantity() >= quantity)
                .filter(batch -> batch.getDueDate().isAfter(LocalDate.now().plusDays(20)))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Insufficient or non-existent units of product with id " + id + " in stock."));
    }

    public Batch findMatchingBatch(Product product){
        return batchRepository.findAll().stream()
                .filter(batch -> batch.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Non existent batch with products of id " + product.getId()));
    }

    public void retrieveProductsFromBatches(PurchaseOrderDTO purchaseOrderDTO) {
        List<Batch> batches = new ArrayList<>();
        for (ProductDTO p : purchaseOrderDTO.getProducts()) {
            Batch batch = findBatchContainingValidProduct(p.getProductId(), p.getQuantity());
            updateCurrentQuantity(batch, p.getQuantity());
            batches.add(batch);
        }
        batchRepository.saveAll(batches);
    }

    public void removeCurrentProducts(List<PurchaseProduct> purchaseProducts) {
        for(PurchaseProduct p : purchaseProducts){
            Batch matchingBatch = findMatchingBatch(p.getProduct());
            returnProducts(matchingBatch, p.getQuantity());
            batchRepository.save(matchingBatch);
        }
    }

    public void updateCurrentQuantity (Batch batch, int quantity){
        batch.setCurrentQuantity(batch.getCurrentQuantity() - quantity);
    }

    public void returnProducts(Batch batch, int quantity){
        batch.setCurrentQuantity(batch.getCurrentQuantity() + quantity);
    }

    @Override
    public Batch create(Batch batch) {
        return batchRepository.save(batch);
    }

    @Override
    public Batch update(Batch batch) {
        Batch foundBatch = findById(batch.getId());
        batch.setId(foundBatch.getId());
        return create(batch);
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public Batch findById(Long id) {
        return batchRepository.findById(id).orElseThrow(() -> new NotFoundException("Batch with id " + id + " not found"));
    }

    @Override
    public List<Batch> findAll() {
        return null;
    }

    public List<BatchDueDateResponseDTO> findByDueDateBetween(int days) {
        LocalDate today = LocalDate.now();
        LocalDate finalDate = today.plusDays(days);
        return batchMapper.mapListDtoReponseToEntity(
                batchRepository.findBatchByDueDateBetween(today, finalDate)
                        .orElse(new ArrayList<>())
        );
    }

    public List<BatchDueDateResponseDTO> findByDueDateCategoryBetweenDates(String category, int days, String order) {
        LocalDate today = LocalDate.now();
        LocalDate finalDate =  today.plusDays(days);
        Pageable pageable = PageRequest.of(0,1000, Sort.Direction.valueOf(order.toUpperCase()), "C.due_Date");

        return batchMapper.mapListDtoReponseToEntity(
                batchRepository.findDueDateBySectionOrdered(category, today,finalDate, pageable).getContent()
        );
    }
}
