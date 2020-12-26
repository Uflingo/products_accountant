package ru.uflingo.products_accountant.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String name;
    private String group;
    private ProductUnits units;
    private Integer amount;
    private LocalDate created = LocalDate.now();
    private LocalDate updated = LocalDate.now();
    private LocalDate lastTimeAdded = LocalDate.now();
}
