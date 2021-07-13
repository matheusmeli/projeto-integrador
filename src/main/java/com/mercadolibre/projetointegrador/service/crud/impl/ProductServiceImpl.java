package com.mercadolibre.projetointegrador.service.crud.impl;

import com.mercadolibre.projetointegrador.dtos.SectionDTO;
import com.mercadolibre.projetointegrador.dtos.WarehouseStockDTO;
import com.mercadolibre.projetointegrador.dtos.response.ProductSectionResponseDTO;
import com.mercadolibre.projetointegrador.dtos.response.SimpleBatchResponseDTO;
import com.mercadolibre.projetointegrador.dtos.response.WarehouseStockResponseDTO;
import com.mercadolibre.projetointegrador.exceptions.NotFoundException;
import com.mercadolibre.projetointegrador.model.*;
import com.mercadolibre.projetointegrador.repository.InboundOrderRepository;
import com.mercadolibre.projetointegrador.repository.ProductRepository;
import com.mercadolibre.projetointegrador.repository.SellerRepository;
import com.mercadolibre.projetointegrador.service.crud.ICRUD;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ICRUD<Product> {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    private final InboundOrderRepository inboundRepository;

    private final EmployeeServiceImpl employeeService;

    private final ModelMapper modelMapper;

    @Override
    public Product create(Product product) {
        return null;
    }

    @Override
    public Product update(Product product) {
        productRepository.findById(product.getId());
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.findById(id);
        productRepository.deleteById(id);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto de id: " + id + " não encontrado"));
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findAllBySellerName(String name) {
        return productRepository.findAllBySeller(sellerRepository.findSellerByName(name));
    }

    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    public List<ProductSectionResponseDTO> findSectionStockByProductId(Long productId, String orderBy, String username) {
        Warehouse warehouse = employeeService.findByUsername(username).getWarehouse();
        Map<Section, List<Batch>> sectionBatches = findBatchStockGroupBySection(findById(productId), warehouse);

        List<ProductSectionResponseDTO> responseDTO = buildProductSectionResponse(sectionBatches, productId, orderBy);

        return responseDTO;
    }

    public WarehouseStockResponseDTO findProductstockInWarehouses(Long productId) {
        List<InboundOrder> filteredInboundOrder = getInboundOrderByProduct(findById(productId), inboundRepository.findAll());

        List<WarehouseStockDTO> stock = getWarehouseStockDTO(filteredInboundOrder, productId);

        return WarehouseStockResponseDTO
                .builder()
                .productId(productId)
                .warehouses(stock)
                .build();
    }

    private List<WarehouseStockDTO> getWarehouseStockDTO(List<InboundOrder> filteredInboundOrder, Long productId) {
        return filteredInboundOrder
                .stream()
                .map(inboundOrder -> new WarehouseStockDTO(
                        inboundOrder.getSection().getWarehouse().getId(),
                        inboundOrder.getBatchStock()
                                .stream()
                                .filter(batch -> batch.getProduct().getId().equals(productId))
                                .collect(Collectors.toList())))
                .collect(Collectors.groupingBy(WarehouseStockDTO::getWarehouseCode))
                .values()
                .stream()
                .map(grouped -> new WarehouseStockDTO(
                        grouped.get(0).getWarehouseCode(),
                        grouped
                                .stream()
                                .map(WarehouseStockDTO::getBatchstock)
                                .flatMap(List::stream)
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    private Map<Section, List<Batch>> findBatchStockGroupBySection(Product product, Warehouse warehouse) {
        List<InboundOrder> inboundOrders = inboundRepository.findAllBySection_Warehouse_Id(warehouse.getId());

        Map<Section, List<Batch>> resultMap = new HashMap<>();
        List<InboundOrder> filteredInboundOrder = getInboundOrderByProduct(product, inboundOrders);

        for (InboundOrder element : filteredInboundOrder) {
            List<Batch> batchStock = getBatchStreamByProduct(product, element).sorted(Comparator.comparing(Batch::getDueDate)).collect(Collectors.toList());

            if (!batchStock.isEmpty() && resultMap.containsKey(element.getSection())) {
                resultMap.get(element.getSection()).addAll(batchStock);
            } else if (!batchStock.isEmpty()) {
                resultMap.put(element.getSection(), batchStock);
            }
        }

        return resultMap;
    }

    private List<InboundOrder> getInboundOrderByProduct(Product product, List<InboundOrder> inboundOrders) {
        List<InboundOrder> filteredInboundOrder = inboundOrders
                .stream()
                .filter(inboundOrder -> getBatchStreamByProduct(product, inboundOrder).findAny().isPresent())
                .collect(Collectors.toList());

        if (filteredInboundOrder.isEmpty())
            throw new NotFoundException("Nenhum batch do produto id: " + product.getId() + " cadastrado em warehouse!");

        return filteredInboundOrder;
    }

    private Stream<Batch> getBatchStreamByProduct(Product product, InboundOrder inboundOrder) {
        return inboundOrder.getBatchStock()
                .stream()
                .filter(batch -> batch.getProduct().getId().equals(product.getId()));
    }

    private List<ProductSectionResponseDTO> buildProductSectionResponse(Map<Section, List<Batch>> sectionBatches, Long productId, String orderBy) {
        List<ProductSectionResponseDTO> builtResponse = new ArrayList<>();
        Comparator<SimpleBatchResponseDTO> sortingComparator = getSortingComparator(orderBy);

        for (Map.Entry<Section, List<Batch>> entry : sectionBatches.entrySet()) {
            SectionDTO sectionDTO = SectionDTO.builder()
                    .id(String.valueOf(entry.getKey().getId()))
                    .warehouseCode(entry.getKey().getWarehouse().getId())
                    .build();

            ProductSectionResponseDTO responseDTO =
                    ProductSectionResponseDTO
                            .builder()
                            .productId(String.valueOf(productId))
                            .section(sectionDTO)
                            .batchStock(entry.getValue()
                                    .stream()
                                    .map(batch -> SimpleBatchResponseDTO.builder()
                                            .id(String.valueOf(batch.getId()))
                                            .dueDate(batch.getDueDate().toString())
                                            .currentQuantity(batch.getCurrentQuantity())
                                            .build())
                                    .sorted(sortingComparator)
                                    .collect(Collectors.toList()))
                            .build();

            builtResponse.add(responseDTO);
        }

        return builtResponse;
    }

    private Comparator<SimpleBatchResponseDTO> getSortingComparator(String orderBy) {
        Comparator<SimpleBatchResponseDTO> sortingComparator = null;

        switch (orderBy) {
            case "C":
                sortingComparator = Comparator.comparing(SimpleBatchResponseDTO::getCurrentQuantity);
                break;
            case "F":
                sortingComparator = Comparator.comparing(SimpleBatchResponseDTO::getDueDate);
                break;
            default:
                sortingComparator = Comparator.comparing(SimpleBatchResponseDTO::getId);
                break;
        }

        return sortingComparator;
    }
}
