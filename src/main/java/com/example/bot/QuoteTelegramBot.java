// QuoteTelegramBot.java
package com.example.quotebot; // Или ваш пакет

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import com.example.quotebot.service.QuoteService;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class QuoteTelegramBot extends TelegramLongPollingBot {

    private final QuoteService quoteService;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botName;

    public QuoteTelegramBot(QuoteService quoteService) {
        this.quoteService = quoteService;
        // ЭТА СТРОКА ОЧЕНЬ ВАЖНА:
        log.info("QuoteTelegramBot успешно инициализирован Spring.");
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // ... ваш код обработки обновлений
    }
}