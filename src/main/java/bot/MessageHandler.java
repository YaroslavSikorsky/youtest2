package bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendContact;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static bot.BotUtils.*;

@Slf4j
@RequiredArgsConstructor
public class MessageHandler {
    private final RequestStorage requestStorage;
    private final TelegramClient telegramClient;
    private final String trainerChatId;
    public NoteStorage noteStorage;

    public BotApiMethod<?> answerMessage(Message message) {
        String chatId = message.getChatId().toString();
        Long userId = message.getFrom().getId();


        if ((message.hasText()) && !(message.getText().equals("/see"))) {
            requestStorage.addNotes(message.getText());
            //noteStorage.addNotes(message.getText());
            System.out.println(message.getText());

        }

        if ((message.hasText()) && (message.getText().equals("/see"))) {
            System.out.println(message.getText());
            return createSelectiomToolMessage2(chatId, message.getText(), noteStorage);

        }

        if ((message.hasText()) && (message.getText().equals("/start"))) {
            return createSelectiomToolMessage(chatId, "Привет! Этот бот предназначен для записи в приложение You Notes. \n\n "
            );
        }
        if ((message.hasContact())) {
            log.info("Контакты: {}", message.getContact());

            Request request = requestStorage.get(userId);
            request.setNumber(message.getContact().getPhoneNumber());
            requestStorage.put(request);

            updateMessageTextRequest(request, trainerChatId, telegramClient);

            SendMessage sendMessage = new SendMessage(chatId, "Ваша заявка отправлена: \n\n" + request + "\nЖелает создать новую заявкку?");
            sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));

            InlineKeyboardRow row = createInlineRow(createInlineButton("Создать новую заявку", "createNewRequest"));
            sendMessage.setReplyMarkup(createInlineMarkup(row));

            return sendMessage;
        }
        log.info(message.getText());
        return null;
    }
}