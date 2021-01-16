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
            .setConsumptionPeriodAmount(productDto.getConsumptionPeriodAmount())
            .setConsumptionPerDay(getConsumptionPerDay(
                productDto.getConsumptionPeriod(), productDto.getConsumption(),
                productDto.getConsumptionPeriodAmount(),
                productDto.getAmount())
            );
    }

    private BigDecimal getConsumptionPerDay(
        ConsumptionPeriod period,
        BigDecimal consumption,
        int consumptionPeriodAmount,
        BigDecimal amount
    ) {
        BigDecimal c = consumption == null ? amount : consumption;
        return c.divide(BigDecimal.valueOf((long) period.getDays() * consumptionPeriodAmount), 5, RoundingMode.UP);
    }
}
