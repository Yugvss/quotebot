package com.example.quotebot.bot;

import com.example.quotebot.model.Quote;
import com.example.quotebot.service.QuoteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Component
public class QuoteTelegramBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final QuoteService quoteService;

    public QuoteTelegramBot(@Value("${bot.token}") String botToken,
                            @Value("${bot.username}") String botUsername,
                            QuoteService quoteService) {
        super(botToken);
        this.botUsername = botUsername;
        this.quoteService = quoteService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Проверяем, есть ли в обновлении сообщение и текст в нем
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    sendWelcomeMessage(chatId);
                    break;
                case "/quote":
                    sendRandomQuote(chatId);
                    break;
                default:
                    sendMessage(chatId, "Неизвестная команда. Используйте /quote, чтобы получить цитату дня.");
                    break;
            }
        }
    }

    private void sendWelcomeMessage(long chatId) {
        String welcomeText = "Привет! Я бот, который присылает цитаты. \n" +
                "Используй команду /quote, чтобы получить свою цитату на день.";
        sendMessage(chatId, welcomeText);
    }

    private void sendRandomQuote(long chatId) {
        Optional<Quote> randomQuoteOpt = quoteService.getRandomQuote();
        if (randomQuoteOpt.isPresent()) {
            Quote quote = randomQuoteOpt.get();
            String quoteText = String.format("\"%s\"\n\n— %s", quote.getText(), quote.getAuthor());
            sendMessage(chatId, quoteText);
        } else {
            sendMessage(chatId, "Извините, в моей базе пока нет цитат. Попробуйте позже.");
        }
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message); // Отправляем сообщение
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}