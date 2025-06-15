package com.example.quotebot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@Slf4j
public class QuoteBotApplication {

    /**
     * @param args аргументы командной строки, позволяет передавать данные прямо при запуске приложения
     */
    public static void main(String[] args) {
        // Эта строка запускает всё приложение Spring Boot.
        SpringApplication.run(QuoteBotApplication.class, args);
        log.info("Приложение запущено");
    }

}