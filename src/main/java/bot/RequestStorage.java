package bot;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class RequestStorage {
    private final Map<Long, Request> requests = Collections.synchronizedMap(new HashMap<>());

    private final NotesRepository notesRepository;

    public RequestStorage(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    public void addNotes(String text) {

        Note note = new Note(text);
        notesRepository.saveNotes(note.getText(), note.getType().toString());

    }

    public List<String> showNotes() {

        return notesRepository.show()  // возвращает List<Note>
                .stream()
                .map(Note::getText)    // преобразуем Note → String
                .collect(Collectors.toList()); //
    }

    public void put(Request request) {
        Long id = request.getUserId();
        requests.put(id, request);
    }

    public Request get(Long id) {
        return requests.get(id);
    }

    public Boolean has(Long id) {
        return requests.containsKey(id);
    }
}