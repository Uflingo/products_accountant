package ru.uflingo.products_accountant.exception;

public class WarehouseNotFoundException extends RuntimeException {
    public WarehouseNotFoundException(String message) {
        super(message);
    }
}
