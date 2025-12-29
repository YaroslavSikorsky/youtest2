package infrastructure;

import domain.Note;
import domain.NoteType;
import domain.NotesRepository;
import domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

    }

    public void addNotes(Note note) {

        notesRepository.saveNotes(note.getText(), note.getType().toString(),note.getUserId());

    }

    public List<NoteType> getType() {

        return listType;
    }
    public List<Note> showNotesByTypeAndUser(NoteType type, UUID userId) {
        // Берём все заметки данного пользователя
        List<Note> userNotes = notesRepository.show(userId);

        // Фильтруем по типу
        return userNotes.stream()
                .filter(n -> n.getType() == type)
                .collect(Collectors.toList());
    }

    public List<Note> showFullNotes(UUID userId) {
        return notesRepository.show(userId);
    }

    public List<Note> showNotesByType(NoteType type) {
        return notesRepository.showByType(type);
    }

//    public List<String> showNotes() {
//
//        return notesRepository.show()  // возвращает List<Note>
//                .stream()
//                .map(Note::getText)    // преобразуем Note → String
//                .collect(Collectors.toList()); //
//    }


}