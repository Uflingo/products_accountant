package ru.uflingo.products_accountant.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.uflingo.products_accountant.domain.Warehouse;

public interface WarehouseRepository extends MongoRepository<Warehouse, String> {
    List<Warehouse> findByUserId(long userId);
}
