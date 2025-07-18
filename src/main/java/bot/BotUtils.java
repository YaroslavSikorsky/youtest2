package bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class BotUtils {

    public static SendMessage createMessage(String chatId, String text) {
        return new SendMessage(chatId, text);
    }

    public static BotApiMethod<?> sendContactButton(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Поделитесь контактом, при помощи нажатия кнопки ниже.");
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();

        KeyboardButton keyboardButton = new KeyboardButton("Поделиться котактом");

        keyboardButton.setRequestContact(true);
        keyboardFirstRow.add(keyboardButton);

        keyboard.add(keyboardFirstRow);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        return sendMessage;
    }

    public static InlineKeyboardButton createInlineButton(String text, String cData) {
        InlineKeyboardButton button = new InlineKeyboardButton(text);
        button.setCallbackData(cData);
        return button;
    }

    public static InlineKeyboardRow createInlineRow(InlineKeyboardButton... buttons) {
        InlineKeyboardRow row = new InlineKeyboardRow();
        for (InlineKeyboardButton button : buttons) {
            row.add(button);
        }
        return row;
    }

    public static InlineKeyboardMarkup createInlineMarkup(InlineKeyboardRow... rows) {
        List<InlineKeyboardRow> listRow = new ArrayList<>();
        for (InlineKeyboardRow row : rows) {
            listRow.add(row);
        }
        return new InlineKeyboardMarkup(listRow);
    }

    public static InlineKeyboardMarkup createTimeButtonsMarkup() {
        List<InlineKeyboardRow> listRow = new ArrayList<>();
        for (int i = 9; i < 20; i = i + 2) {
            listRow.add(createInlineRow(
                    createInlineButton(i + ":00 - " + (i + 1) + ":00", i + "-" + (i + 1)),
                    createInlineButton((i + 1) + ":00 - " + (i + 2) + ":00", (i + 1) + ":00 - " + (i + 2) + ":00")));
        }
        return new InlineKeyboardMarkup(listRow);
    }

    public static EditMessageText createEditMessageText(String text, String chatId, Integer messageId) {
        EditMessageText editMessageText = new EditMessageText(text);
        editMessageText.setMessageId(messageId);
        editMessageText.setChatId(chatId);
        return editMessageText;
    }

    public static Integer sendMessageToTrenerChat(Request request, TelegramClient telegramClient, String trainerChatId) {
        if (request != null) {
            try {
                Message message = telegramClient.execute(new SendMessage(trainerChatId, request.toString()));
                log.info("Запрос отправлен");
                return message.getMessageId();
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        log.info("Запрос не получилось отправить");
        return null;
    }

    public static void updateMessageTextRequest(Request request, String chatId, TelegramClient telegramClient) {
        if (request != null) {
            try {
                telegramClient.execute(createEditMessageText(request.toString(), chatId, request.getMessageId()));
                log.info("Запрос обновлен");
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public static BotApiMethod<?> createSelectiomToolMessage(String chatId, String text) {
        SendMessage sendMessage = createMessage(chatId, text);

        InlineKeyboardRow row1 = createInlineRow(createInlineButton("Отправить: " + text, NoteType.SEND.toString()));
        InlineKeyboardRow row2 = createInlineRow(createInlineButton("Посмотреть все записи", NoteType.SEE.toString()));

        sendMessage.setReplyMarkup(createInlineMarkup(row1, row2));

        return sendMessage;
    }

    public static BotApiMethod<?> createSelectiomToolMessage2(String chatId, String text, NoteStorage noteStorage) {
        SendMessage sendMessage = createMessage(chatId, text);

        // InlineKeyboardRow row1 = createInlineRow(createInlineButton("Отправить: " + text, NoteType.SEND.toString()));
        InlineKeyboardRow row2 = createInlineRow(createInlineButton("Посмотреть все записи", NoteType.SEE.toString()));

        sendMessage.setReplyMarkup(createInlineMarkup(row2));

        return sendMessage;
    }
}