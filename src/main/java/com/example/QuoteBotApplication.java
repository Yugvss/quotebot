package com.example.quotebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class QuoteBotApplication {

    /**
     * @param args аргументы командной строки, позволяет передавать данные прямо при запуске приложения
     */
    public static void main(String[] args) {
        // Эта строка запускает всё приложение Spring Boot.
        SpringApplication.run(QuoteBotApplication.class, args);
    }

}