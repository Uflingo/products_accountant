package ru.uflingo.products_accountant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.uflingo.products_accountant.converter.ProductConverter;
import ru.uflingo.products_accountant.domain.warehouse.Warehouse;
import ru.uflingo.products_accountant.dto.ProductDto;
import ru.uflingo.products_accountant.repo.WarehouseRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final WarehouseRepository warehouseRepository;

    @Transactional
    public ProductDto addProduct(long userId, String warehouseName, ProductDto productDto) {
        log.info("Adding productDto {} for user {} to warehouse {}", productDto, userId, warehouseName);
        Warehouse warehouse = warehouseRepository.findByUserIdAndName(userId, warehouseName)
            .orElseThrow(() -> new IllegalArgumentException("No warehouse " + warehouseName + " for user " + warehouseName));
        if (warehouse.getProducts().stream().anyMatch(p -> p.getName().equals(productDto.getName()))) {
            throw new IllegalArgumentException("There is already productDto with warehouseName " + productDto.getName());
        }
        warehouse.getProducts().add(ProductConverter.toDomain(productDto));
        log.info("Successfully added productDto {} for user {} to warehouse {}", productDto, userId, warehouseName);
        warehouseRepository.save(warehouse);
        return productDto;
    }
}
