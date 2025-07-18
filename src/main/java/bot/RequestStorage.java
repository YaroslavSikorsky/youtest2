package bot;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class RequestStorage {
    private final Map<Long, Request> requests = Collections.synchronizedMap(new HashMap<>());
    public List<Note> notes = new ArrayList<>();

    public void addNotes(String text) {
        notes.add(new Note(text));
    }

    public List<Note> showNotes() {
        return notes;
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