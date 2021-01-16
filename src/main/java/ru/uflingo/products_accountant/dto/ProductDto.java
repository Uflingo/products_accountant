package ru.uflingo.products_accountant.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.uflingo.products_accountant.domain.product.ConsumptionPeriod;
import ru.uflingo.products_accountant.domain.product.ProductUnits;

@Data
@Accessors(chain = true)
public class ProductDto {
    private String name;
    private String group;
    private ProductUnits units;
    private BigDecimal amount;
    private BigDecimal consumption;
    private ConsumptionPeriod consumptionPeriod = ConsumptionPeriod.WEEK;
    private int consumptionPeriodAmount = 1;
    private LocalDateTime created;
    private boolean isDeleted;
}
