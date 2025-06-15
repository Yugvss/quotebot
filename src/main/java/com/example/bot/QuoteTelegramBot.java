package com.example.quotebot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class QuoteTelegramBot extends TelegramLongPollingBot {

    private final QuoteService quoteService;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botName;

    @Autowired
    public QuoteTelegramBot(QuoteService quoteService) {
        this.quoteService = quoteService;
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

            log.info("Сообщение от chatId {}: {}", chatId, messageText);

            if (messageText.equals("/start")) {
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));
                message.setText("Привет! Я QuoteBot. Отправьте /quote, чтобы получить случайную цитату.");
                try {
                    execute(message);
                    log.info("Ответ отправлен chatId {}: {}", chatId);
                } catch (TelegramApiException e) {
                    log.error("Ошибка при отправке ответа chatId {}: {}", chatId, e.getMessage());
                }
            } else if (messageText.equals("/quote")) {
                // Получаем случайную цитату из QuoteService
                String quote = quoteService.getRandomQuote(); // Assuming you have this method in QuoteService

                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));
                message.setText(quote);  // Отправляем цитату пользователю
                try {
                    execute(message);
                    log.info("Цитата отправлена chatId {}: {}", chatId);
                } catch (TelegramApiException e) {
                    log.error("Ошибка при отправке цитаты chatId {}: {}", chatId, e.getMessage());
                }

            }
            else {
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));
                message.setText("Я не понимаю эту команду.");
                try {
                    execute(message);
                    log.info("Ответ об неизвестной команде отправлен chatId {}: {}", chatId);
                } catch (TelegramApiException e) {
                    log.error("Ошибка при отправке ответа об неизвестной команде chatId {}: {}", chatId, e.getMessage());
                }
            }
        }
    }
}