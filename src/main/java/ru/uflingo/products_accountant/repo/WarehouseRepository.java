package ru.uflingo.products_accountant.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.uflingo.products_accountant.domain.warehouse.Warehouse;

public interface WarehouseRepository extends MongoRepository<Warehouse, String> {
    List<Warehouse> findByUserId(long userId);

    Optional<Warehouse> findByUserIdAndName(long userId, String name);

    Optional<Warehouse> findByUserIdAndIsDefaultTrue(long userId);
}
