package bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
public class CallbackQueryHandler {
    private final RequestStorage requestStorage;
    private final TelegramClient telegramClient;
    private final String trainerChatId;

    private final List<String> timeList = List.of("9:00 - 10:00", "10:00 - 11:00", "11:00 - 12:00", "12:00 - 13:00 ",
            "13:00 - 14:00", "14:00 - 15:00", "15:00 - 16:00", "16:00 - 17:00", "17:00 - 18:00", "19:00 - 20:00", "20:00 - 21:00");


    public BotApiMethod<?> processCallbackQuery(Update update) {
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        Long userId = update.getCallbackQuery().getFrom().getId();

// пока убрал тк не правильно сохраняю класс нотес а сохраняю просто текст
        if (update.getCallbackQuery().getData().equals(UtilType.SEE.toString())) {
            System.out.println("SEE is used");

            List<String> notes = requestStorage.showNotes();
            StringBuilder sb = new StringBuilder();

            if (notes.isEmpty()) {
                sb.append("Нет сохранённых записей.");
            } else {
                sb.append("Список записей:\n\n");
                for (int i = 0; i < notes.size(); i++) {
                    sb.append(i + 1).append(". ").append(notes.get(i)).append("\n");
                }
            }
            SendMessage sendMessage = SendMessage.builder()
                    .chatId(chatId)
                    .text(sb.toString())
                    .build();

            sendMessage.setChatId(chatId);
            sendMessage.setText(sb.toString());

            return sendMessage;
        }

        return null;
    }
}