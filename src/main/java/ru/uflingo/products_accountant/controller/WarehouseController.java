package ru.uflingo.products_accountant.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.uflingo.products_accountant.domain.Warehouse;
import ru.uflingo.products_accountant.domain.WarehouseName;
import ru.uflingo.products_accountant.service.WarehouseService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping("/{userId}")
    public List<Warehouse> getUserWarehouses(@PathVariable long userId) {
        return warehouseService.getUserWarehouses(userId);
    }

    @PutMapping("/{userId}")
    public void createWarehouse(@PathVariable long userId, @RequestBody WarehouseName name) {
        warehouseService.createWarehouse(userId, name.getName());
    }
}
