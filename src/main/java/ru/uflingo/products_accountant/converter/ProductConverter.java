package ru.uflingo.products_accountant.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.experimental.UtilityClass;
import ru.uflingo.products_accountant.domain.product.ConsumptionPeriod;
import ru.uflingo.products_accountant.domain.product.Product;
import ru.uflingo.products_accountant.dto.ProductDto;

@UtilityClass
public class ProductConverter {
    public ProductDto toDto(Product product) {
        return ProductDto.builder()
            .name(product.getName())
            .group(product.getGroup())
            .units(product.getUnits())
            .amount(product.getAmount())
            .consumption(product.getConsumption())
            .consumptionPeriod(product.getConsumptionPeriod())
            .build();
    }

    public Product toDomain(ProductDto productDto) {
        return new Product()
            .setName(productDto.getName())
            .setGroup(productDto.getGroup())
            .setUnits(productDto.getUnits())
            .setAmount(productDto.getAmount())
            .setConsumption(productDto.getConsumption())
            .setConsumptionPeriod(productDto.getConsumptionPeriod())
            .setConsumptionPerDay(getConsumptionPerDay(productDto.getConsumptionPeriod(), productDto.getConsumption()));
    }

    private BigDecimal getConsumptionPerDay(ConsumptionPeriod period, BigDecimal consumption) {
        return consumption.divide(BigDecimal.valueOf(period.getDays()), 5,  RoundingMode.UP);
    }
}
