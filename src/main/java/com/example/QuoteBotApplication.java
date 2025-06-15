package com.example;

import lombok.extern.slf4j.Slf4j; // Убедитесь, что эта строка есть
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j // Эта аннотация для логера
public class QuoteBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuoteBotApplication.class, args);
        log.info("Приложение запущено"); // Это ваше сообщение
    }
}