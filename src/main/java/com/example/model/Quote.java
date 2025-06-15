package com.example.model;

import jakarta.persistence.*; // Используем jakarta.persistence для Spring Boot 3+
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity // Указывает, что это JPA-сущность, соответствующая таблице в БД
@Table(name = "quotes") // Явно указываем имя таблицы в БД
@Data // Lombok: автоматически генерирует геттеры, сеттеры, toString(), equals(), hashCode()
@NoArgsConstructor // Lombok: генерирует конструктор без аргументов
@AllArgsConstructor // Lombok: генерирует конструктор со всеми аргументами
public class Quote {

    @Id // Помечает поле как первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоматическая генерация ID базой данных (для PostgreSQL)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT") // Поле 'text' не может быть null, тип TEXT
    private String text;

    @Column(length = 255) // Поле 'author', максимальная длина 255 символов
    private String author;
}