package com.example.quotebot;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuoteService {

    private final List<String> quotes = new ArrayList<>();

    public QuoteService() {
        // Добавляем несколько цитат для примера
        quotes.add("Будущее принадлежит тем, кто верит в красоту своей мечты. - Eleanor Roosevelt");
        quotes.add("Единственный способ сделать великое дело - любить то, что ты делаешь. - Steve Jobs");
        quotes.add("Жизнь - это то, что происходит с тобой, пока ты строишь другие планы. - John Lennon");
    }

    public String getRandomQuote() {
        if (quotes.isEmpty()) {
            return "Нет доступных цитат.";
        }
        Random random = new Random();
        int randomIndex = random.nextInt(quotes.size());
        return quotes.get(randomIndex);
    }
}