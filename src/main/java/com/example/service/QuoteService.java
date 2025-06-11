package com.example.quotebot.service;

import com.example.quotebot.model.Quote;
import com.example.quotebot.repository.InMemoryQuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuoteService {

    private final InMemoryQuoteRepository repository;

    @Autowired
    public QuoteService(InMemoryQuoteRepository repository) {
        this.repository = repository;
    }

    public List<Quote> getAllQuotes() {
        return repository.findAll();
    }

    public Optional<Quote> getQuoteById(Long id) {
        return repository.findById(id);
    }

    public Quote createQuote(Quote quote) {
        return repository.save(quote);
    }

    public Optional<Quote> updateQuote(Long id, Quote updatedQuote) {
        return repository.findById(id)
                .map(quote -> {
                    quote.setText(updatedQuote.getText());
                    quote.setAuthor(updatedQuote.getAuthor());
                    return repository.save(quote);
                });
    }

    public boolean deleteQuote(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Quote> getRandomQuote() {
        return repository.findRandom();
    }
}