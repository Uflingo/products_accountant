package ru.uflingo.products_accountant.dto;

import java.math.BigDecimal;

import ru.uflingo.products_accountant.domain.product.Period;

public class ProductDtoProvider {
    public static final ProductDto PRODUCT_DTO_VEG = new ProductDto()
        .setGroup("vegetable")
        .setName("cucumber")
        .setAmount(BigDecimal.valueOf(3))
        .setConsumptionPeriod(Period.WEEK)
        .setConsumption(BigDecimal.valueOf(2));
}
