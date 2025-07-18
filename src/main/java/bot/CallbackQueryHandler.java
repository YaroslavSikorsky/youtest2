package bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.MaybeInaccessibleMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;

import static bot.BotUtils.*;

@Slf4j
@RequiredArgsConstructor
public class CallbackQueryHandler {
    private final RequestStorage requestStorage;
    private final TelegramClient telegramClient;
    private final String trainerChatId;
    public NoteStorage noteStorage;
    private final List<String> timeList = List.of("9:00 - 10:00", "10:00 - 11:00", "11:00 - 12:00", "12:00 - 13:00 ",
            "13:00 - 14:00", "14:00 - 15:00", "15:00 - 16:00", "16:00 - 17:00", "17:00 - 18:00", "19:00 - 20:00", "20:00 - 21:00");


    public BotApiMethod<?> processCallbackQuery(Update update) {
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        Long userId = update.getCallbackQuery().getFrom().getId();


        if (update.getCallbackQuery().getData().equals(NoteType.SEE.toString())) {
            System.out.println("SEE is used");

            List<Note> notes = requestStorage.showNotes();
            StringBuilder sb = new StringBuilder();

            if (notes.isEmpty()) {
                sb.append("Нет сохранённых записей.");
            } else {
                sb.append("Список записей:\n\n");
                for (int i = 0; i < notes.size(); i++) {
                    sb.append(i + 1).append(". ").append(notes.get(i).getText()).append("\n");
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

        if (InlineCalendarCommandUtil.isInlineCalendarClicked(update) && !InlineCalendarCommandUtil.isCalendarIgnoreButtonClicked(update)) {
            if (InlineCalendarCommandUtil.isCalendarNavigationButtonClicked(update)) {
                InlineCalendarBuilder inlineCalendarBuilder = new InlineCalendarBuilder(LanguageEnum.RU);

                inlineCalendarBuilder.setShowFullMonthName(true);


                EditMessageText editMessageText = createEditMessageText("Выберите дату.", chatId, messageId);
                editMessageText.setReplyMarkup(inlineCalendarBuilder.build(update));
                return editMessageText;
            } else {
                Request request = requestStorage.get(userId);
                request.setDate(InlineCalendarCommandUtil.extractDate(update).toString());
                requestStorage.put(request);

                updateMessageTextRequest(request, trainerChatId, telegramClient);

                EditMessageText editMessageText = createEditMessageText("Выберите время тренеровки: ", chatId, messageId);
                editMessageText.setReplyMarkup(createTimeButtonsMarkup());
                return editMessageText;
            }
        }

        if (update.getCallbackQuery().getData().equals(Tools.Skis.toString()) || update.getCallbackQuery().getData().equals(Tools.Snowboard.toString())) {
            InlineCalendarBuilder inlineCalendarBuilder = new InlineCalendarBuilder(LanguageEnum.RU);
            inlineCalendarBuilder.setShowFullMonthName(true);

            Request request = new Request(
                    userId,
                    update.getCallbackQuery().getFrom().getFirstName() + " " + update.getCallbackQuery().getFrom().getLastName(),
                    "",
                    update.getCallbackQuery().getData(),
                    "",
                    "",
                    null
            );

            request.setMessageId(sendMessageToTrenerChat(request, telegramClient, trainerChatId));

            requestStorage.put(request);

            EditMessageText editMessageText = createEditMessageText("Выберите дату.", chatId, messageId);
            editMessageText.setReplyMarkup(inlineCalendarBuilder.build(update));
            return editMessageText;
        }

        if (timeList.contains(update.getCallbackQuery().getData())) {
            Request request = requestStorage.get(userId);
            request.setTime(update.getCallbackQuery().getData());
            requestStorage.put(request);

            updateMessageTextRequest(request, trainerChatId, telegramClient);

            try {
                telegramClient.execute(new DeleteMessage(chatId, messageId));
                log.info("Сообщение удалено.");
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            return sendContactButton(chatId);
        }

        if (update.getCallbackQuery().getData().equals("createNewRequest")) {
            return createSelectiomToolMessage(chatId, "Выберите на чем хотите кататься:");
        }
        return null;
    }
}