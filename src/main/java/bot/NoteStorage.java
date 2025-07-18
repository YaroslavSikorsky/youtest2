package bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NoteStorage {
    public List<Note> notes = new ArrayList<>();


    public void addNotes(String text) {
        notes.add(new Note(text));
    }

    public void showNotes() {
        System.out.println(notes.toString());
    }
}
