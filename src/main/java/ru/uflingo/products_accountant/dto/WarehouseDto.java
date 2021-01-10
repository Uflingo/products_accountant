package ru.uflingo.products_accountant.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WarehouseDto {
    private String name;
    private boolean isDefault;
    private LocalDateTime created;
}
