package com.example.repository;

import com.example.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Помечает интерфейс как компонент репозитория Spring
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    // предоставляет базовые методы CRUD


    // 'nativeQuery = true' означает, что это чистый SQL-запрос
    @Query(value = "SELECT * FROM quotes ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Quote> findRandomQuote();
}