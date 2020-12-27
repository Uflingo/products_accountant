package ru.uflingo.products_accountant.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.uflingo.products_accountant.domain.Warehouse;
import ru.uflingo.products_accountant.repo.WarehouseRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public List<Warehouse> getUserWarehouses(long userId) {
        return warehouseRepository.findByUserId(userId);
    }

    @Transactional
    public void createWarehouse(long userId, String name) {
        log.info("Creating warehouse for user {} with name {}", userId, name);
        Warehouse warehouse = Warehouse.builder()
        .userId(userId)
        .name(name)
        .build();
        List<Warehouse> userWarehouses = warehouseRepository.findByUserId(userId);
        if (userWarehouses.stream().anyMatch(wh -> wh.getName().equals(name))) {
            throw new IllegalArgumentException("There is a warehouse with name " + name);
        }
        if (userWarehouses.stream().noneMatch(Warehouse::isDefault)) {
            warehouse.setDefault(true);
        }
        warehouseRepository.insert(warehouse);
    }
}
