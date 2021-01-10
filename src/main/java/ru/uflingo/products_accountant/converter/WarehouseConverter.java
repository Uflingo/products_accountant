package ru.uflingo.products_accountant.converter;

import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;
import ru.uflingo.products_accountant.domain.warehouse.Warehouse;
import ru.uflingo.products_accountant.dto.WarehouseDto;
import ru.uflingo.products_accountant.dto.WarehouseFullDto;

@UtilityClass
public class WarehouseConverter {
    public WarehouseDto toDto(Warehouse warehouse) {
        return WarehouseDto.builder()
            .isDefault(warehouse.isDefault())
            .name(warehouse.getName())
            .created(warehouse.getCreated())
            .build();
    }

    public WarehouseFullDto toFullDto(Warehouse warehouse) {
        return WarehouseFullDto.builder()
            .isDefault(warehouse.isDefault())
            .name(warehouse.getName())
            .created(warehouse.getCreated())
            .products(warehouse.getProducts()
                .stream()
                .filter(p -> !p.isDeleted())
                .map(ProductConverter::toDto)
                .collect(Collectors.toList())
            )
            .build();
    }
}
