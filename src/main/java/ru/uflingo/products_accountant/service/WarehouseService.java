package ru.uflingo.products_accountant.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

    public void createWarehouse(long userId, String name) {
        log.info("Creating warehouse for user {} with name {}", userId, name);
        Warehouse warehouse = new Warehouse();
        warehouse.setUserId(userId);
        warehouse.setName(name);
        warehouse.setDefault(true);
        warehouseRepository.insert(warehouse);
    }
}
