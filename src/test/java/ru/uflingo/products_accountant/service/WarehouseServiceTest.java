package ru.uflingo.products_accountant.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.uflingo.products_accountant.converter.WarehouseConverter;
import ru.uflingo.products_accountant.domain.warehouse.Warehouse;
import ru.uflingo.products_accountant.dto.WarehouseDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.uflingo.products_accountant.domain.WarehouseProvider.getDeletedWarehouse;
import static ru.uflingo.products_accountant.domain.WarehouseProvider.getFirstWarehouse;
import static ru.uflingo.products_accountant.domain.WarehouseProvider.getSecondWarehouse;

@SpringBootTest
public class WarehouseServiceTest {
    private final long USER_ID = 3L;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(Warehouse.class);
    }

    @Test
    void createWarehouse_single_success() {
        String warehouseName = "myWarehouseName";
        warehouseService.createWarehouse(USER_ID, warehouseName);

        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(USER_ID));
        List<Warehouse> warehouses = mongoTemplate.find(query, Warehouse.class);

        Warehouse expected = new Warehouse()
            .setName(warehouseName)
            .setUserId(USER_ID)
            .setDefault(true)
            .setDeleted(false)
            .setProducts(Collections.emptyList());

        assertThat(warehouses.size()).isEqualTo(1);
        Warehouse warehouse = warehouses.get(0);
        assertThat(warehouse).isEqualToIgnoringGivenFields(expected, "id", "created", "updated");
    }

    @Test
    void createWarehouse_duplicate_exception() {
        String warehouseName = "myWarehouseName";
        warehouseService.createWarehouse(USER_ID, warehouseName);
        assertThatThrownBy(
            () -> warehouseService.createWarehouse(USER_ID, warehouseName),
            "")
            .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void createWarehouse_two_firstDefault() {
        String firstName = "firstWarehouseName";
        String secondName = "secondWarehouseName";
        warehouseService.createWarehouse(USER_ID, firstName);
        warehouseService.createWarehouse(USER_ID, secondName);

        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(USER_ID));
        List<Warehouse> warehouses = mongoTemplate.find(query, Warehouse.class);

        assertThat(warehouses.size()).isEqualTo(2);
        Optional<Warehouse> dbFirstWarehouse =
            warehouses.stream().filter(wh -> wh.getName().equals(firstName)).findFirst();
        assertThat(dbFirstWarehouse).isPresent();
        assertThat(dbFirstWarehouse.get().isDefault()).isTrue();

        Optional<Warehouse> dbSecondWarehouse =
            warehouses.stream().filter(wh -> wh.getName().equals(secondName)).findFirst();
        assertThat(dbSecondWarehouse).isPresent();
        assertThat(dbSecondWarehouse.get().isDefault()).isFalse();
    }

    @Test
    void getUserWarehousesTest() {
        Warehouse firstWarehouse = getFirstWarehouse();
        Warehouse secondWarehouse = getSecondWarehouse();
        Warehouse deletedWarehouse = getDeletedWarehouse();
        Warehouse differentUsersWh = new Warehouse().setUserId(USER_ID + 1).setName("randomName").setDefault(true).setDeleted(false);
        mongoTemplate.save(firstWarehouse);
        mongoTemplate.save(secondWarehouse);
        mongoTemplate.save(deletedWarehouse);
        mongoTemplate.save(differentUsersWh);

        List<WarehouseDto> userWarehouses = warehouseService.getUserWarehouses(USER_ID);

        assertThat(userWarehouses).usingElementComparatorIgnoringFields("created")
            .containsOnly(WarehouseConverter.toDto(firstWarehouse), WarehouseConverter.toDto(secondWarehouse));
    }
}
