package com.example.bot;

import com.example.service.QuoteService; // Импорт сервиса цитат
import com.example.dto.QuoteDTO; // Импорт QuoteDTO
import com.example.exception.QuoteNotFoundException; // исключения

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
@Slf4j
public class QuoteTelegramBot extends TelegramLongPollingBot {

    //имя пользователя и токен из application
    @Value("${telegram.bot.username}")
    private String botUsername;
    @Value("${telegram.bot.token}")
    private String botToken;

    // QuoteService через конструктор
    private final QuoteService quoteService;

    // Конструктор, который Spring будет использовать для создания экземпляра бота
    public QuoteTelegramBot(
            @Value("${telegram.bot.token}") String botToken,
            QuoteService quoteService // + QuoteService
    ) {
        super(botToken); // Вызываем конструктор родительского класса с токеном
        this.quoteService = quoteService;
        log.info("QuoteTelegramBot успешно инициализирован Spring.");
    }

    /**
     * Этот метод вызывается каждый раз, когда бот получает новое обновление от Telegram.
     * Здесь содержится вся основная логика обработки входящих сообщений/команд.
     */
    @Override
    public void onUpdateReceived(Update update) {
        log.info("Received update: {}", update.toString()); // Логируем входящее обновление

        // Проверяем, есть ли сообщение и содержит ли оно текст
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            log.info("Processing message. Chat ID: {}, Text: {}", chatId, messageText);

            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId)); // Устанавливаем ID чата, куда отправить ответ

            // Логика обработки команд
            if ("/quote".equals(messageText)) {
                try {
                    // Получаем QuoteDTO и извлекаем  текст
                    QuoteDTO randomQuote = quoteService.getRandomQuote();
                    String quoteFormatted = randomQuote.getText();
                    if (randomQuote.getAuthor() != null && !randomQuote.getAuthor().isEmpty()) {
                        quoteFormatted += "\n— " + randomQuote.getAuthor();
                    }
                    message.setText(quoteFormatted);
                } catch (QuoteNotFoundException e) {
                    log.warn("Не удалось получить цитату: {}", e.getMessage());
                    message.setText("Извините, сейчас цитат нет или произошла ошибка. Попробуйте позже.");
                } catch (Exception e) { // Ловимеще ошибки при получении цитаты
                    log.error("Неизвестная ошибка при получении цитаты для chat ID {}: {}", chatId, e.getMessage(), e);
                    message.setText("Произошла внутренняя ошибка при попытке получить цитату. Мы уже работаем над этим!");
                }
            } else {
                message.setText("Вы сказали: " + messageText); // Простая заглушка (эхо)
            }

            try {
                execute(message); // Отправляем ответное сообщение
                log.info("Message sent successfully to chat ID: {}", chatId);
            } catch (TelegramApiException e) {
                log.error("Ошибка при отправке сообщения в Telegram для chat ID {}: {}", chatId, e.getMessage(), e);
            }
        } else {

            log.warn("Получено обновление, но это не текстовое сообщение (или нет текста): {}", update.toString());
        }
    }

    /**
     * Возвращает имя пользователя бота.
     */
    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }
}