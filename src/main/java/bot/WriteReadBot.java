package bot;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.webhook.TelegramWebhookBot;

import java.util.List;


@Slf4j
@Getter
@Setter
public class WriteReadBot implements TelegramWebhookBot {

    private String botPath;
    private String botUsername;
    private String botToken;
    private SetWebhook setWebhook;
    private TelegramClient telegramClient;
    private List<String> list;
    private StringBuilder textForMessage = new StringBuilder();
    private Integer messageId;

    private bot.MessageHandler messageHandler;
    private CallbackQueryHandler callbackQueryHandler;

    public WriteReadBot(SetWebhook setWebhook, bot.MessageHandler messageHandler, CallbackQueryHandler callbackQueryHandler, String botToken, String botPath, String botUsername, TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
        this.setWebhook = setWebhook;
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
        this.botToken = botToken;
        this.botPath = botPath;
        this.botUsername = botUsername;
    }

    @Override
    public void runDeleteWebhook() {

    }

    @Override
    public void runSetWebhook() {

    }


    @Override
    public BotApiMethod<?> consumeUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            return callbackQueryHandler.processCallbackQuery(update);
        } else {
            Message message = update.getMessage();
            if (message != null) {
                return messageHandler.answerMessage(message);
            }
        }
        return null;
    }
}