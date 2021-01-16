package ru.uflingo.products_accountant.controller;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.uflingo.products_accountant.dto.ExceptionDto;
import ru.uflingo.products_accountant.exception.ProductNotFoundException;
import ru.uflingo.products_accountant.exception.WarehouseNotFoundException;

@ControllerAdvice
public class RestResponseEntityErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {DuplicateKeyException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        ExceptionDto response = new ExceptionDto(HttpStatus.CONFLICT, ex.getMessage());
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {WarehouseNotFoundException.class, ProductNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        ExceptionDto response = new ExceptionDto(HttpStatus.NOT_FOUND, ex.getMessage());
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
