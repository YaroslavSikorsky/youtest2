package bot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Configuration
@Getter
public class TelegramConfig {

    @Value("${telegram.webhook-path}")
    private String webhookPath;

    @Value("${telegram.bot-name}")
    private String botName;

    @Value("${telegram.bot-token}")
    private String botToken;

    @Value("${telegram.trainer-chat-id}")
    private String trainerChatId;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(webhookPath).build();
    }

    @Bean
    public TelegramClient telegramClient() {
        return new OkHttpTelegramClient(botToken);
    }

    @Bean
    public MessageHandler messageHandler(RequestStorage requestStorage, TelegramClient telegramClient) {
        return new MessageHandler(requestStorage, telegramClient, trainerChatId);
    }

    @Bean
    public RequestStorage requestStorage() {
        return new RequestStorage();
    }

    @Bean
    public NoteStorage noteStorage() {
        return new NoteStorage();
    }

    @Bean
    public CallbackQueryHandler callbackQueryHandler(RequestStorage requestStorage, TelegramClient telegramClient) {
        return new CallbackQueryHandler(requestStorage, telegramClient, trainerChatId);
    }

    @Bean
    public WriteReadBot springWebhookBot(SetWebhook setWebhook,
                                         MessageHandler messageHandler,
                                         CallbackQueryHandler callbackQueryHandler,
                                         TelegramClient telegramClient) {
        return new WriteReadBot(setWebhook, messageHandler, callbackQueryHandler, botToken, webhookPath, botName, telegramClient);
    }
}