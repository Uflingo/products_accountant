package ru.uflingo.products_accountant.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.uflingo.products_accountant.domain.warehouse.WarehouseName;
import ru.uflingo.products_accountant.dto.WarehouseDto;
import ru.uflingo.products_accountant.service.WarehouseService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("warehouse")
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping("/{userId}")
    public List<WarehouseDto> getUserWarehouses(@PathVariable long userId) {
        return warehouseService.getUserWarehouses(userId);
    }

    @PutMapping("/{userId}")
    public WarehouseDto createWarehouse(@PathVariable long userId, @RequestBody WarehouseName warehouseName) {
        return warehouseService.createWarehouse(userId, warehouseName.getName());
    }
}
