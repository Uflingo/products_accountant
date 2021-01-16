package ru.uflingo.products_accountant.domain.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Product {
    private String name;
    private String group;
    private ProductUnits units;
    private BigDecimal amount;
    private BigDecimal consumption;
    private ConsumptionPeriod consumptionPeriod;
    private int consumptionPeriodAmount;
    private BigDecimal consumptionPerDay;
    private LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    private LocalDateTime updated = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    private LocalDateTime lastTimeAdded = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    private boolean isDeleted = false;
}
