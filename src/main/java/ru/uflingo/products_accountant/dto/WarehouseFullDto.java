package ru.uflingo.products_accountant.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WarehouseFullDto {
    private String name;
    private boolean isDefault;
    private LocalDateTime created;
    private List<ProductDto> products;
}
