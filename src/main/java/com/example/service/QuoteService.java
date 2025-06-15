// QuoteService.java
package com.example.service; // Предполагается, что ваш Service в пакете 'service'

import com.example.model.Quote; // * ОБЯЗАТЕЛЬНО УБЕДИТЕСЬ, ЧТО ЭТОТ ПАКЕТ ВЕРЕН *
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class QuoteService {

    // Изначально здесь был private final List<String> quotes = new ArrayList<>();
    // Теперь мы храним объекты Quote
    private final List<Quote> quotes = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(); // Для генерации уникальных ID

    public QuoteService() {
        // Добавляем несколько цитат для примера
        // Убедитесь, что класс Quote имеет конструктор или сеттеры для id, text, author
        quotes.add(new Quote(idCounter.incrementAndGet(), "Будущее принадлежит тем, кто верит в красоту своей мечты.", "Eleanor Roosevelt"));
        quotes.add(new Quote(idCounter.incrementAndGet(), "Единственный способ сделать великое дело - любить то, что ты делаешь.", "Steve Jobs"));
        quotes.add(new Quote(idCounter.incrementAndGet(), "Жизнь - это то, что происходит с тобой, пока ты строишь другие планы.", "John Lennon"));
    }

    public String getRandomQuote() {
        if (quotes.isEmpty()) {
            return "Нет доступных цитат.";
        }
        Random random = new Random();
        int randomIndex = random.nextInt(quotes.size());
        // Возвращаем только текст цитаты из объекта Quote
        return quotes.get(randomIndex).getText();
    }

    // --- Методы, которые вызываются QuoteController ---

    public List<Quote> getAllQuotes() {
        return new ArrayList<>(quotes); // Возвращаем копию списка, чтобы избежать внешних модификаций
    }

    public Optional<Quote> getQuoteById(Long id) {
        return quotes.stream()
                .filter(q -> q.getId().equals(id))
                .findFirst();
    }

    public Quote createQuote(Quote quote) {
        // Устанавливаем новый ID, если он не установлен (или равен 0)
        if (quote.getId() == null || quote.getId() == 0) {
            quote.setId(idCounter.incrementAndGet());
        } else {
            // Если ID уже задан, убедимся, что он уникален.
            // В реальном приложении здесь может быть более сложная логика,
            // например, бросание исключения, если ID уже существует.
            // Для примера просто генерируем новый, если есть конфликт.
            if (getQuoteById(quote.getId()).isPresent()) {
                quote.setId(idCounter.incrementAndGet());
            }
        }
        quotes.add(quote);
        return quote;
    }

    public Optional<Quote> updateQuote(Long id, Quote updatedQuote) {
        Optional<Quote> existingQuoteOpt = getQuoteById(id);
        if (existingQuoteOpt.isPresent()) {
            Quote existingQuote = existingQuoteOpt.get();
            existingQuote.setText(updatedQuote.getText());
            existingQuote.setAuthor(updatedQuote.getAuthor()); // Предполагая, что у Quote есть поле 'author'
            // Если у Quote есть другие поля, их тоже нужно обновить
            return Optional.of(existingQuote);
        }
        return Optional.empty();
    }

    public boolean deleteQuote(Long id) {
        // Удаляем цитату по ID и возвращаем true, если удаление произошло
        return quotes.removeIf(q -> q.getId().equals(id));
    }
}