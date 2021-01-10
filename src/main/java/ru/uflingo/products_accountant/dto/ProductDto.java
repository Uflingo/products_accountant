package ru.uflingo.products_accountant.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.uflingo.products_accountant.domain.product.ConsumptionPeriod;
import ru.uflingo.products_accountant.domain.product.ProductUnits;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private String name;
    private String group;
    private ProductUnits units;
    private BigDecimal amount;
    private BigDecimal consumption;
    private ConsumptionPeriod consumptionPeriod;
    private LocalDateTime created;
    private boolean isDeleted;
}
