package ru.uflingo.products_accountant.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.uflingo.products_accountant.domain.warehouse.Warehouse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WarehouseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MongoTemplate mongoTemplate;

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(Warehouse.class);
    }

    @Test
    void createWarehouseTest() throws Exception {
        mockMvc.perform(put("/warehouse/3").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"myWarehouse\"}"))
            .andExpect(status().isOk());

        List<Warehouse> all = mongoTemplate.findAll(Warehouse.class);
        Warehouse expected =
            new Warehouse()
                .setUserId(3)
                .setName("myWarehouse")
                .setDefault(true)
                .setProducts(new ArrayList<>());
        Optional<Warehouse> first = all.stream().findFirst();
        assertThat(first).isPresent();
        assertThat(first.get()).isEqualToIgnoringGivenFields(expected, "id", "created", "updated");
    }
}
