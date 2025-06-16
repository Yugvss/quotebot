package com.example.service;

import com.example.dto.QuoteDTO;
import com.example.exception.QuoteNotFoundException;
import com.example.mapper.QuoteMapper;
import com.example.model.Quote;
import com.example.repository.QuoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // Автоматически инжектит QuoteRepository и QuoteMapper
@Slf4j // Для логирования
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final QuoteMapper quoteMapper; // Инжектим маппер

    @Transactional(readOnly = true) // Транзакция только для чтения
    public QuoteDTO getRandomQuote() {
        log.info("Запрос на получение случайной цитаты.");
        return quoteRepository.findRandomQuote()
                .map(quoteMapper::toDto) // Маппим Entity в DTO
                .orElseThrow(() -> new QuoteNotFoundException("Цитаты не найдены в базе данных."));
    }

    @Transactional // Транзакция для записи
    public QuoteDTO addQuote(QuoteDTO quoteDTO) {
        log.info("Добавление новой цитаты: {}", quoteDTO.getText());
        Quote quote = quoteMapper.toEntity(quoteDTO); // Маппим DTO в Entity
        Quote savedQuote = quoteRepository.save(quote); // Сохраняем в БД
        log.info("Quote saved successfully with ID: {}", savedQuote.getId());
        return quoteMapper.toDto(savedQuote); // Возвращаем сохраненную цитату как DTO
    }
}
