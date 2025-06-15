// QuoteTelegramBot.java
package com.example.bot; // Или ваш пакет

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import com.example.service.QuoteService;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText("Вы сказали: " + messageText); // Простая заглушка

            try {
                execute(message); // Отправляем ответ
            } catch (TelegramApiException e) {
                e.printStackTrace(); // Логируйте ошибки отправки
            }
        }
    }
}