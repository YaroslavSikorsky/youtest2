package infrastructure;

import domain.Note;
import domain.NoteType;
import domain.NotesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class NotesStorage {

    private final NotesRepository notesRepository;

    private List<NoteType> listType = new ArrayList<>();

    public NotesStorage(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;

        listType.add(NoteType.GAME);
        listType.add(NoteType.HOME);
        listType.add(NoteType.FILMS);
        listType.add(NoteType.WISHLIST);
        listType.add(NoteType.PARENT);
        listType.add(NoteType.APARTMENT);
        listType.add(NoteType.TODO);
        listType.add(NoteType.PROJECT);
        listType.add(NoteType.PLACE);
    }

    // Создание заметки
    public void addNotes(Note note) {
        notesRepository.saveNotes(
                note.getId(),
                note.getTitle(),
                note.getText(),
                note.getType().toString(),
                note.getUserId(),
                note.getStatus(),
                note.getDone(),
                note.getCalendar(),
                note.getCalendarDate(),
                LocalDateTime.now()
        );
    }

    // Получение типов заметок
    public List<NoteType> getType() {
        return listType;
    }

    // Получение заметок по типу и пользователю
    public List<Note> showNotesByTypeAndUser(NoteType type, UUID userId) {
        List<Note> userNotes = notesRepository.show(userId);
        return userNotes.stream()
                .filter(n -> n.getType() == type)
                .collect(Collectors.toList());
    }

    // Получение всех заметок пользователя
    public List<Note> showFullNotes(UUID userId) {
        return notesRepository.show(userId);
    }

    // Получение заметок по типу
    public List<Note> showNotesByType(NoteType type) {
        return notesRepository.showByType(type);
    }

    // Получение заметки по ID
    public Optional<Note> getNoteById(UUID id) {
        return notesRepository.findById(id);
    }

    // Обновление заметки
    public void updateNote(UUID id, Note note) {
        notesRepository.updateNote(
                id,
                note.getTitle(),
                note.getText(),
                note.getType().toString(),
                note.getStatus(),
                note.getDone(),
                note.getCalendar(),
                note.getCalendarDate()
        );
    }

    // Обновление только статуса (для drag & drop)
    public void updateNoteStatus(UUID id, String status) {
        notesRepository.updateStatus(id, status);
    }

    // Удаление заметки
    public void deleteNote(UUID id) {
        notesRepository.deleteNote(id);
    }

    // Получить заметки для календаря
    public List<Note> getCalendarNotes(UUID userId) {
        return notesRepository.getCalendarNotes(userId);
    }

    // Получить заметки на конкретную дату
    public List<Note> getNotesByDate(UUID userId, LocalDate date) {
        return notesRepository.getNotesByDate(userId, date);
    }
}