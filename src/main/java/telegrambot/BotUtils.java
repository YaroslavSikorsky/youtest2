//package telegrambot;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@RequiredArgsConstructor
//public class BotUtils {
//
//    public static SendMessage createMessage(String chatId, String text) {
//        return new SendMessage(chatId, text);
//    }
//
//    public static InlineKeyboardButton createInlineButton(String text, String cData) {
//        InlineKeyboardButton button = new InlineKeyboardButton(text);
//        button.setCallbackData(cData);
//        return button;
//    }
//
//    public static InlineKeyboardRow createInlineRow(InlineKeyboardButton... buttons) {
//        InlineKeyboardRow row = new InlineKeyboardRow();
//        for (InlineKeyboardButton button : buttons) {
//            row.add(button);
//        }
//        return row;
//    }
//
//    public static InlineKeyboardMarkup createInlineMarkup(InlineKeyboardRow... rows) {
//        List<InlineKeyboardRow> listRow = new ArrayList<>();
//        for (InlineKeyboardRow row : rows) {
//            listRow.add(row);
//        }
//        return new InlineKeyboardMarkup(listRow);
//    }
//
//    public static BotApiMethod<?> createSelectiomToolMessage(String chatId, String text) {
//        SendMessage sendMessage = createMessage(chatId, text);
//
//        InlineKeyboardRow row1 = createInlineRow(createInlineButton("Отправить: " + text, UtilType.SEND.toString()));
//        InlineKeyboardRow row2 = createInlineRow(createInlineButton("Посмотреть все записи", UtilType.SEE.toString()));
//
//        sendMessage.setReplyMarkup(createInlineMarkup(row1, row2));
//
//        return sendMessage;
//    }
//
//    public static BotApiMethod<?> createSelectiomToolMessage2(String chatId, String text) {
//        SendMessage sendMessage = createMessage(chatId, text);
//
//        InlineKeyboardRow row2 = createInlineRow(createInlineButton("Посмотреть все записи", UtilType.SEE.toString()));
//
//        sendMessage.setReplyMarkup(createInlineMarkup(row2));
//
//        return sendMessage;
//    }
//}