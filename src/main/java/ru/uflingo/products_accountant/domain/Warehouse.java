package ru.uflingo.products_accountant.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("warehouse")
public class Warehouse {
    private long userId;
    private String name;
    private boolean isDefault;
    private List<Product> products;
}
