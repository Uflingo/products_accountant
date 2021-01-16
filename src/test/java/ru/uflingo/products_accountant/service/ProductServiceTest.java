package ru.uflingo.products_accountant.service;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.uflingo.products_accountant.converter.ProductConverter;
import ru.uflingo.products_accountant.domain.WarehouseProvider;
import ru.uflingo.products_accountant.domain.warehouse.Warehouse;
import ru.uflingo.products_accountant.dto.ProductDto;
import ru.uflingo.products_accountant.dto.WarehouseFullDto;
import ru.uflingo.products_accountant.exception.WarehouseNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.uflingo.products_accountant.domain.ProductProvider.PRODUCT_VEG;
import static ru.uflingo.products_accountant.domain.WarehouseProvider.getFirstWarehouse;
import static ru.uflingo.products_accountant.dto.ProductDtoProvider.PRODUCT_DTO_VEG;

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
            .isInstanceOf(WarehouseNotFoundException.class);
    }

    @Test
    void addProduct_success() {
        addDefaultWarehouse();
        productService.addProduct(USER_ID, DEFAULT_WAREHOUSE_NAME, PRODUCT_DTO_VEG);

        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(USER_ID).and("name").is(DEFAULT_WAREHOUSE_NAME));
        Warehouse warehouse = mongoTemplate.findOne(query, Warehouse.class);
        assertThat(warehouse).isNotNull();

        assertThat(warehouse.getProducts()).usingElementComparatorIgnoringFields("created", "updated", "lastTimeAdded")
            .containsOnly(ProductConverter.toDomain(PRODUCT_DTO_VEG));
    }

    @Test
    void addProductToDefault_success() {
        addDefaultWarehouse();
        productService.addProduct(USER_ID, PRODUCT_DTO_VEG);

        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(USER_ID).and("isDefault").is(true));
        Warehouse warehouse = mongoTemplate.findOne(query, Warehouse.class);
        assertThat(warehouse).isNotNull();

        assertThat(warehouse.getProducts()).usingElementComparatorIgnoringFields("created", "updated", "lastTimeAdded")
            .containsOnly(ProductConverter.toDomain(PRODUCT_DTO_VEG));
    }

    @Test
    void addProduct_duplicateName_exception() {
        addDefaultWarehouse();
        productService.addProduct(USER_ID, DEFAULT_WAREHOUSE_NAME, PRODUCT_DTO_VEG);

        assertThatThrownBy(() -> productService
            .addProduct(USER_ID, DEFAULT_WAREHOUSE_NAME, PRODUCT_DTO_VEG), "")
            .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void getProductsFromDefault() {
        Warehouse firstWarehouse = getFirstWarehouse();
        firstWarehouse.setProducts(List.of(PRODUCT_VEG));
        mongoTemplate.save(firstWarehouse);

        WarehouseFullDto productsFromDefault = productService.getProductsFromDefault(USER_ID);

        System.out.println(productsFromDefault);

        assertThat(productsFromDefault).isNotNull();
        assertThat(productsFromDefault.getProducts())
            .usingElementComparatorIgnoringFields("created", "updated", "lastTimeAdded")
            .containsOnly(ProductConverter.toDto(PRODUCT_VEG));
    }

    @Test
    void getProductsFromNamed() {
        Warehouse firstWarehouse = getFirstWarehouse();
        firstWarehouse.setProducts(List.of(PRODUCT_VEG));
        mongoTemplate.save(firstWarehouse);

        WarehouseFullDto productsFromNamed = productService.getProductsFromNamed(USER_ID, DEFAULT_WAREHOUSE_NAME);

        System.out.println(productsFromNamed);

        assertThat(productsFromNamed).isNotNull();
        assertThat(productsFromNamed.getProducts())
            .usingElementComparatorIgnoringFields("created", "updated", "lastTimeAdded")
            .containsOnly(ProductConverter.toDto(PRODUCT_VEG));
    }

    void addDefaultWarehouse() {
        mongoTemplate.save(WarehouseProvider.getFirstWarehouse());
    }
}
