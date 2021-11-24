package ru.uflingo.products_accountant.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.uflingo.products_accountant.converter.ProductConverter;
import ru.uflingo.products_accountant.converter.WarehouseConverter;
import ru.uflingo.products_accountant.domain.product.Product;
import ru.uflingo.products_accountant.domain.warehouse.Warehouse;
import ru.uflingo.products_accountant.dto.ProductDto;
import ru.uflingo.products_accountant.dto.WarehouseFullDto;
import ru.uflingo.products_accountant.exception.WarehouseNotFoundException;
import ru.uflingo.products_accountant.repo.WarehouseRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final WarehouseRepository warehouseRepository;

    @Transactional
    public ProductDto addProduct(long userId, ProductDto productDto) {
        Warehouse warehouse = warehouseRepository.findByUserIdAndIsDefaultTrue(userId)
            .orElseThrow(() -> new WarehouseNotFoundException("No warehouse default for user " + userId));
        return addProduct(userId, warehouse.getName(), productDto);
    }

    @Transactional
    public ProductDto addProduct(long userId, String warehouseName, ProductDto productDto) {
        log.info("Adding productDto {} for user {} to warehouse {}", productDto, userId, warehouseName);
        Warehouse warehouse = warehouseRepository.findByUserIdAndName(userId, warehouseName)
            .orElseThrow(() -> new WarehouseNotFoundException("No warehouse " + warehouseName + " for user " + userId));
        if (warehouse.getProducts().stream().anyMatch(p -> p.getName().equals(productDto.getName()))) {
            throw new DuplicateKeyException("There is already productDto with warehouseName " + productDto.getName());
        }
        warehouse.getProducts().add(ProductConverter.toDomain(productDto));
        log.info("Successfully added productDto {} for user {} to warehouse {}", productDto, userId, warehouseName);
        warehouseRepository.save(warehouse);
        return productDto;
    }

    @Transactional(readOnly = true)
    public WarehouseFullDto getProductsFromDefault(long userId) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findByUserIdAndIsDefaultTrue(userId);
        return optionalWarehouse.map(WarehouseConverter::toFullDto).orElse(null);
    }

    @Transactional(readOnly = true)
    public WarehouseFullDto getProductsFromNamed(long userId, String name) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findByUserIdAndName(userId, name);
        return optionalWarehouse.map(WarehouseConverter::toFullDto).orElse(null);
    }

    @Transactional(readOnly = true)
    public WarehouseFullDto getProductsOnShortage(long userId, int days) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findByUserIdAndIsDefaultTrue(userId);
        if (optionalWarehouse.isEmpty()) {
            return null;
        }
        Warehouse warehouse = optionalWarehouse.get();
        List<Product> productsOnShortage = warehouse.getProducts().stream()
            .filter(p ->
                p.getAmount().compareTo(p.getConsumptionPerDay().multiply(BigDecimal.valueOf(days))) <= 0)
            .collect(Collectors.toList());
        return WarehouseConverter.toFullDto(warehouse, productsOnShortage);
    }
}
