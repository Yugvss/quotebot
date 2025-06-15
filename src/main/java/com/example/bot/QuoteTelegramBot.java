package com.example.bot; // Убедитесь, что это ваш реальный пакет

import com.example.service.QuoteService; // Импорт вашего сервиса цитат
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

    // Внедряем имя пользователя и токен из application.properties
    @Value("${telegram.bot.username}")
    private String botUsername;
    @Value("${telegram.bot.token}")
    private String botToken;

    // Внедряем QuoteService через конструктор
    private final QuoteService quoteService; // <-- Добавлено поле для QuoteService

    // Конструктор, который Spring будет использовать для создания экземпляра бота
    public QuoteTelegramBot(
            @Value("${telegram.bot.token}") String botToken, // Получаем токен для суперкласса
            QuoteService quoteService // Внедряем QuoteService
    ) {
        super(botToken); // Вызываем конструктор родительского класса с токеном
        this.quoteService = quoteService; // Присваиваем внедренный сервис полю класса
        log.info("QuoteTelegramBot успешно инициализирован Spring.");
    }

    /**
     * Этот метод вызывается каждый раз, когда бот получает новое обновление от Telegram.
     * Здесь содержится вся основная логика обработки входящих сообщений/команд.
     */
    @Override // <-- Важная аннотация, указывающая на переопределение
    public void onUpdateReceived(Update update) {
        log.info("Received update: {}", update.toString()); // Логируем входящее обновление

        // Проверяем, есть ли сообщение и содержит ли оно текст
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            log.info("Processing message. Chat ID: {}, Text: {}", chatId, messageText);

            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId)); // Устанавливаем ID чата, куда отправить ответ

            // Простая логика: если пользователь отправил "/quote", даем цитату, иначе - эхо
            if ("/quote".equals(messageText)) {
                message.setText(quoteService.getRandomQuote()); // Используем сервис для получения цитаты
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
            // Логируем обновления, которые не являются текстовыми сообщениями
            log.warn("Получено обновление, но это не текстовое сообщение (или нет текста): {}", update.toString());
        }
    }

    /**
     * Возвращает имя пользователя бота.
     * Должно соответствовать @username вашего бота, выданному BotFather.
     */
    @Override
    public String getBotUsername() {
        return botUsername;
    }

    /**
     * Возвращает токен бота.
     * Должен соответствовать API-токену, выданному BotFather.
     */
    @Override
    public String getBotToken() {
        return this.botToken;
    }
}