package ru.uflingo.products_accountant.domain.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Product {
    private String name;
    private String group;
    private ProductUnits units;
    private BigDecimal amount;
    private BigDecimal consumption;
    private ConsumptionPeriod consumptionPeriod;
    private BigDecimal consumptionPerDay;
    private LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    private LocalDateTime updated = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    private LocalDateTime lastTimeAdded = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    private boolean isDeleted = false;
}
