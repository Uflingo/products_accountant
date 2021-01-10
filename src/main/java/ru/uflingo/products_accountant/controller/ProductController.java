package ru.uflingo.products_accountant.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.uflingo.products_accountant.dto.ProductDto;
import ru.uflingo.products_accountant.service.ProductService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    @PostMapping(path = "/{userId}/{warehouseName}", consumes = "application/json")
    public ProductDto addProduct(@PathVariable long userId,
                           @PathVariable String warehouseName,
                           @RequestBody ProductDto product) {
        log.info("Got post for product {}", product);
        return productService.addProduct(userId, warehouseName, product);
    }
}
