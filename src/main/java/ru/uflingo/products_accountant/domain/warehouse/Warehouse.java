package ru.uflingo.products_accountant.domain.warehouse;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.uflingo.products_accountant.domain.product.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Document("warehouse")
public class Warehouse {
    @Id
    private ObjectId id;
    private long userId;
    private String name;
    private boolean isDefault;
    private boolean isDeleted;
    private LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    private LocalDateTime updated = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    private List<Product> products = new ArrayList<>();
}
