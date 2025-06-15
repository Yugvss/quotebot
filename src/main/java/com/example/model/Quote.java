// Quote.java
package com.example.model; // * ОБЯЗАТЕЛЬНО УБЕДИТЕСЬ, ЧТО ЭТОТ ПАКЕТ ВЕРЕН *

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quote {
    private Long id;
    private String text;
    private String author; // Добавляем поле для автора, если оно используется
}