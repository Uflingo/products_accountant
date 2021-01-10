package ru.uflingo.products_accountant.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ConsumptionPeriod {
    DAY(1),
    WEEK(7),
    MONTH(30),
    YEAR(365);

    @Getter
    private int days;
}
