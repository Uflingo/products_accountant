package ru.uflingo.products_accountant.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.uflingo.products_accountant.domain.Product;
import ru.uflingo.products_accountant.repo.ProductRepository;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("/{name}")
    public List<Product> getProduct(@PathVariable String name) {
        log.info("Got request for name {}", name);
        List<Product> byName = productRepository.findByName(name);
        log.info("Got from db {}", byName);
        return byName;
    }

    @PostMapping(consumes = "application/json")
    public Product addProduct(@RequestBody Product product) {
        log.info("Got post for product {}", product);
        Product insert = productRepository.insert(product);
        log.info("From db {}", insert);
        return insert;
    }
}
