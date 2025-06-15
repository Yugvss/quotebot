package com.example.repository;

import com.example.model.Quote;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryQuoteRepository {
    // Потокобезопасная мапа для хранения цитат <ID, Цитата>
    private final Map<Long, Quote> quotes = new ConcurrentHashMap<>();
    // Потокобезопасный счетчик для генерации ID
    private final AtomicLong counter = new AtomicLong();

    // Предзаполним несколькими цитатами для старта
    public InMemoryQuoteRepository() {
        save(new Quote(null, "The only way to do great work is to love what you do.", "Steve Jobs"));
        save(new Quote(null, "Strive not to be a success, but rather to be of value.", "Albert Einstein"));
        save(new Quote(null, "The mind is everything. What you think you become.", "Buddha"));
    }

    public List<Quote> findAll() {
        return new ArrayList<>(quotes.values());
    }

    public Optional<Quote> findById(Long id) {
        return Optional.ofNullable(quotes.get(id));
    }

    public Quote save(Quote quote) {
        if (quote.getId() == null) {
            // Создание новой цитаты
            long newId = counter.incrementAndGet();
            quote.setId(newId);
        }
        // Сохранение новой или обновление существующей
        quotes.put(quote.getId(), quote);
        return quote;
    }

    public void deleteById(Long id) {
        quotes.remove(id);
    }

    public Optional<Quote> findRandom() {
        if (quotes.isEmpty()) {
            return Optional.empty();
        }
        List<Quote> quoteList = new ArrayList<>(quotes.values());
        Random random = new Random();
        return Optional.of(quoteList.get(random.nextInt(quoteList.size())));
    }
}