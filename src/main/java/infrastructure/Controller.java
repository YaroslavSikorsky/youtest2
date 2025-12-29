package infrastructure;

import domain.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import domain.Note;
import domain.NoteType;
//import telegrambot.RequestStorage;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/notes")
public class Controller {

    @Autowired
    private NotesStorage notesStorage;

    @Autowired
    private UserStorage userStorage;

    //    @GetMapping
//    public List<Note> getAllRecords() {
//        System.out.println("GET запрос на /notes");  // Добавь логирование
//
//        return requestStorage.showFullNotes();
//    }


    @GetMapping()
    public List<Note> getNotes(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "userId", required = true) String userIdStr  // получаем строку
    ) {
        System.out.println("GET запрос на /notes с типом: " + type + " и userId: " + userIdStr);

        // Конвертируем строку в UUID
        UUID userId;
        try {
            userId = UUID.fromString(userIdStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Некорректный UUID: " + userIdStr);
            return Collections.emptyList();
        }

        if (type == null || type.isEmpty()) {
            // возвращаем все заметки данного пользователя
            return notesStorage.showFullNotes(userId);
        } else {
            try {
                NoteType noteType = NoteType.valueOf(type);
                return notesStorage.showNotesByTypeAndUser(noteType, userId);
            } catch (IllegalArgumentException e) {
                System.out.println("Некорректный тип: " + type);
                return Collections.emptyList();
            }
        }
    }


    @PostMapping("/add")
    public void addRecord(@RequestBody Note note) {
        System.out.println(note);

        notesStorage.addNotes(note);

    }

    @GetMapping("/type")
    public List<NoteType> getType() {
        System.out.println("GET запрос на /type");  // Добавь логирование
        return notesStorage.getType();
    }

    @PostMapping("/users")
    public void addUser(@RequestBody User user) {
        userStorage.addUser(user.getEmail(), user.getPassword());
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {

        System.out.println("ASDASDASDASDASDASDASD");
        User found = userStorage.getUser(user.getEmail());
//        System.out.println(found.toString());
        if (found == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Пользователь не найден");
        }

        if (!found.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Неверный пароль");

        }

        // пароль верный
        return ResponseEntity.ok(found);
    }




}
