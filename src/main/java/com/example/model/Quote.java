package com.example.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity // Указывает, что это JPA-сущность, соответствующая таблице в БД
@Table(name = "quotes")
@Data // Lombok: автоматически генерирует геттеры, сеттеры, toString(), equals(), hashCode()
@NoArgsConstructor // lombok: генерирует конструктор без аргументов
@AllArgsConstructor // Lombok: генерирует конструктор со всеми аргументами
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоматическая генерация ID базой
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(length = 255) // Поле 'author', максимальная длина 255 символов
    private String author;
}