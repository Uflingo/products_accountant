package ru.uflingo.products_accountant.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.uflingo.products_accountant.dto.ProductDto;
import ru.uflingo.products_accountant.dto.WarehouseFullDto;
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
        log.info("Got post user {} warehouse {} product {}", userId, warehouseName, product);
        return productService.addProduct(userId, warehouseName, product);
    }

    @PostMapping(path = "/{userId}", consumes = "application/json")
    public ProductDto addProduct(@PathVariable long userId, @RequestBody ProductDto product) {
        log.info("Got post user {} default warehouse product {}", userId, product);
        return productService.addProduct(userId, product);
    }
    @GetMapping("/{userId}")
    public WarehouseFullDto getProductsFromDefault(@PathVariable long userId) {
        return productService.getProductsFromDefault(userId);
    }

    @GetMapping("/{userId}/{warehouseName}")
    public WarehouseFullDto getProductsFromNamedWarehouse(@PathVariable long userId, @PathVariable String warehouseName) {
        return productService.getProductsFromNamed(userId, warehouseName);
    }
}
