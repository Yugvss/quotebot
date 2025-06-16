package com.example.exception; // Убедитесь, что пакет правильный

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Помечает исключение HTTP-статусом 404 (Not Found)
@ResponseStatus(HttpStatus.NOT_FOUND)
public class QuoteNotFoundException extends RuntimeException {

    // Конструктор, принимает сообщение об ошибке
    public QuoteNotFoundException(String message) {
        super(message);
    }

    //  конструктор с причиной исключения
    public QuoteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}