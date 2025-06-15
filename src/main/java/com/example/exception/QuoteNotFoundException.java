package com.example.exception; // Убедитесь, что пакет правильный

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Помечает исключение HTTP-статусом 404 (Not Found),
// который будет возвращен клиенту, если это исключение не будет перехвачено
@ResponseStatus(HttpStatus.NOT_FOUND)
public class QuoteNotFoundException extends RuntimeException {

    // Конструктор, который принимает сообщение об ошибке
    public QuoteNotFoundException(String message) {
        super(message);
    }

    // Если хотите, можно добавить конструктор с причиной исключения
    public QuoteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}