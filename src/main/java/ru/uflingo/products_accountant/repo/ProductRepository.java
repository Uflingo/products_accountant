package ru.uflingo.products_accountant.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.uflingo.products_accountant.domain.Product;

public interface ProductRepository extends MongoRepository<Product, Long> {
    List<Product> findByName(String name);
}
