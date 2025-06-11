package com.example.quotebot; // Находится в корневом пакете

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения.
 * Аннотация @SpringBootApplication включает три ключевые функции:
 * 1. @EnableAutoConfiguration: Включает механизм автоконфигурации Spring Boot.
 *    Spring пытается "угадать" и сконфигурировать бины, которые вам могут понадобиться.
 *    Например, видя зависимость Spring Web, он настраивает встроенный веб-сервер Tomcat.
 *
 * 2. @ComponentScan: Включает сканирование компонентов. Spring ищет другие компоненты,
 *    конфигурации и сервисы в пакете, где находится этот класс, и во всех его подпакетах.
 *    Именно так он находит наш QuoteController, QuoteService и т.д.
 *
 * 3. @Configuration: Позволяет регистрировать дополнительные бины (beans) в контексте или
 *    импортировать другие классы конфигурации.
 */
@SpringBootApplication
public class QuoteBotApplication {

    /**
     * Метод main() — это стандартная точка входа для любого Java-приложения.
     * @param args аргументы командной строки.
     */
    public static void main(String[] args) {
        // Эта строка запускает всё приложение Spring Boot.
        // Она создает контекст приложения, выполняет сканирование компонентов,
        // запускает автоконфигурацию и стартует встроенный веб-сервер.
        SpringApplication.run(QuoteBotApplication.class, args);
    }

}