package ru.uflingo.products_accountant.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.uflingo.products_accountant.converter.WarehouseConverter;
import ru.uflingo.products_accountant.domain.warehouse.Warehouse;
import ru.uflingo.products_accountant.dto.WarehouseDto;
import ru.uflingo.products_accountant.dto.WarehouseFullDto;
import ru.uflingo.products_accountant.repo.WarehouseRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public List<WarehouseDto> getUserWarehouses(long userId) {
        List<Warehouse> warehouses = warehouseRepository.findByUserId(userId);
        return warehouses.stream()
            .filter(wh -> !wh.isDeleted())
            .sorted(Comparator.comparing(Warehouse::isDefault).reversed().thenComparing(Warehouse::getCreated))
            .map(WarehouseConverter::toDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public WarehouseDto createWarehouse(long userId, String name) {
        log.info("Creating warehouse for user {} with name {}", userId, name);
        Warehouse warehouse = new Warehouse()
            .setUserId(userId)
            .setName(name);
        List<Warehouse> userWarehouses = warehouseRepository.findByUserId(userId);
        if (userWarehouses.stream().anyMatch(wh -> wh.getName().equals(name))) {
            throw new DuplicateKeyException("There is a warehouse with name " + name);
        }
        if (userWarehouses.stream().noneMatch(Warehouse::isDefault)) {
            warehouse.setDefault(true);
        }
        warehouseRepository.save(warehouse);
        return WarehouseConverter.toDto(warehouse);
    }
}
