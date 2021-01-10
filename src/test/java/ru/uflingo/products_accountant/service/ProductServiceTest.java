package ru.uflingo.products_accountant.service;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.uflingo.products_accountant.converter.ProductConverter;
import ru.uflingo.products_accountant.domain.product.ConsumptionPeriod;
import ru.uflingo.products_accountant.domain.warehouse.Warehouse;
import ru.uflingo.products_accountant.dto.ProductDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class ProductServiceTest {
    private final long USER_ID = 3L;
    private final String DEFAULT_WAREHOUSE_NAME = "firstWarehouseName";

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(Warehouse.class);
    }

    @Autowired
    private ProductService productService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void addProduct_noWarehouse_exception() {
        assertThatThrownBy(() -> productService.addProduct(USER_ID, "myWh", new ProductDto()), "")
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void addProduct_success() {
        addDefaultWarehouse();
        ProductDto productDto = ProductDto.builder()
            .group("vegetable")
            .name("cucumber")
            .amount(BigDecimal.valueOf(3))
            .consumptionPeriod(ConsumptionPeriod.WEEK)
            .consumption(BigDecimal.valueOf(2))
            .build();
        productService.addProduct(USER_ID, DEFAULT_WAREHOUSE_NAME, productDto);

        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(USER_ID).and("name").is(DEFAULT_WAREHOUSE_NAME));
        List<Warehouse> warehouses = mongoTemplate.find(query, Warehouse.class);
        assertThat(warehouses.size()).isEqualTo(1);
        Warehouse warehouse = warehouses.get(0);

        assertThat(warehouse.getProducts()).usingElementComparatorIgnoringFields("created", "updated", "lastTimeAdded")
            .containsOnly(ProductConverter.toDomain(productDto));
    }

    @Test
    void addProduct_duplicateName_exception() {
        addDefaultWarehouse();
        ProductDto productDto = ProductDto.builder()
            .group("vegetable")
            .name("cucumber")
            .amount(BigDecimal.valueOf(3))
            .consumptionPeriod(ConsumptionPeriod.WEEK)
            .consumption(BigDecimal.valueOf(2))
            .build();
        productService.addProduct(USER_ID, DEFAULT_WAREHOUSE_NAME, productDto);

        assertThatThrownBy(() -> productService.addProduct(USER_ID, DEFAULT_WAREHOUSE_NAME, productDto), "")
            .isInstanceOf(IllegalArgumentException.class);
    }

    void addDefaultWarehouse() {
        mongoTemplate.save(
            new Warehouse().setUserId(USER_ID).setName(DEFAULT_WAREHOUSE_NAME).setDefault(true).setDeleted(false)
        );
    }
}
