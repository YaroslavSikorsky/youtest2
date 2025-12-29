//package telegrambot;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
//import org.telegram.telegrambots.meta.api.objects.message.Message;
//import org.telegram.telegrambots.meta.generics.TelegramClient;
//
//import static telegrambot.BotUtils.*;
//
//@Slf4j
//@RequiredArgsConstructor
//public class MessageHandler {
////    private final RequestStorage requestStorage;
//    private final TelegramClient telegramClient;
//    private final String trainerChatId;
//
//    public BotApiMethod<?> answerMessage(Message message) {
//        String chatId = message.getChatId().toString();
//        Long userId = message.getFrom().getId();
//
//
////        if ((message.hasText()) && !(message.getText().equals("/see")) && !(message.getText().equals("/start"))) {
////            requestStorage.addNotes(message.getText());
////            System.out.println(message.getText());
////        }
//
//        if ((message.hasText()) && (message.getText().equals("/see")) && !(message.getText().equals("/start"))) {
//            System.out.println(message.getText());
//            return createSelectiomToolMessage2(chatId, message.getText());
//        }
//
//        if ((message.hasText()) && (message.getText().equals("/start"))) {
//            return createSelectiomToolMessage(chatId, "Привет! Этот бот предназначен для записи в приложение You Notes. \n\n "
//            );
//        }
//
//        return null;
//    }
//}