package com.example.controller;

import com.example.dto.QuoteDTO;
import com.example.service.QuoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // REST контроллер
@RequestMapping("/quotes") // Базовый путь для всех эндпоинтов
@RequiredArgsConstructor // Автоматически инжектит QuoteService
@Slf4j
public class QuoteController {

    private final QuoteService quoteService;

    @GetMapping("/random") // GET-запрос на /quotes/random
    public ResponseEntity<QuoteDTO> getRandomQuote() {
        log.info("Получен запрос на /quotes/random");
        QuoteDTO randomQuote = quoteService.getRandomQuote();
        return ResponseEntity.ok(randomQuote); // Возвращаем HTTP 200 OK
    }

    @PostMapping // POST-запрос на /quotes
    public ResponseEntity<QuoteDTO> addQuote(@RequestBody QuoteDTO quoteDTO) {
        log.info("Получен запрос на добавление цитаты: {}", quoteDTO.getText());
        QuoteDTO newQuote = quoteService.addQuote(quoteDTO);
        return new ResponseEntity<>(newQuote, HttpStatus.CREATED); // Возвращаем HTTP 201 Created
    }
}