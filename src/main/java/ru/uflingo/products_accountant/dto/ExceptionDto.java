package ru.uflingo.products_accountant.dto;

import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
public class ExceptionDto {
    HttpStatus code;
    String message;
}
