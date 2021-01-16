package ru.uflingo.products_accountant.domain;

import java.math.BigDecimal;

import ru.uflingo.products_accountant.domain.product.ConsumptionPeriod;
import ru.uflingo.products_accountant.domain.product.Product;
import ru.uflingo.products_accountant.domain.product.ProductUnits;

public class ProductProvider {
    public static final Product PRODUCT_VEG = new Product()
        .setGroup("vegetable")
        .setName("cucumber")
        .setUnits(ProductUnits.KG)
        .setAmount(BigDecimal.valueOf(3))
        .setConsumption(BigDecimal.valueOf(2))
        .setConsumptionPeriod(ConsumptionPeriod.WEEK)
        .setConsumptionPeriodAmount(1)
        .setConsumptionPerDay(BigDecimal.valueOf(0.28571))
        .setConsumption(BigDecimal.valueOf(2));

}
