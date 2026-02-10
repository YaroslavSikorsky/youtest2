package infrastructure;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import domain.Note;
import domain.NoteType;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/notes")
public class Controller {

    @Autowired
    private NotesStorage notesStorage;

    @Autowired
    private UserStorage userStorage;

    // ========== ЗАМЕТКИ ==========

    // Получить заметки с фильтрацией
    @GetMapping()
    public List<Note> getNotes(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "userId", required = true) String userIdStr
    ) {
        System.out.println("GET запрос на /notes с типом: " + type + " и userId: " + userIdStr);

        UUID userId;
        try {
            userId = UUID.fromString(userIdStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Некорректный UUID: " + userIdStr);
            return Collections.emptyList();
        }

        if (type == null || type.isEmpty()) {
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

    // Создать новую заметку
    @PostMapping("/add")
    public ResponseEntity<?> addRecord(@RequestBody Note note) {
        System.out.println("POST /notes/add: " + note);

        try {
            notesStorage.addNotes(note);
            return ResponseEntity.ok("Заметка создана");
        } catch (Exception e) {
            System.err.println("Ошибка при создании заметки: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при создании заметки");
        }
    }

    // Получить заметку по ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getNote(@PathVariable UUID id) {
        System.out.println("GET /notes/" + id);

        return notesStorage.getNoteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Обновить заметку
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(@PathVariable UUID id, @RequestBody Note note) {
        System.out.println("PUT /notes/" + id + ": " + note);

        try {
            // Проверяем, существует ли заметка
            if (notesStorage.getNoteById(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            notesStorage.updateNote(id, note);
            return ResponseEntity.ok("Заметка обновлена");
        } catch (Exception e) {
            System.err.println("Ошибка при обновлении заметки: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при обновлении заметки");
        }
    }

    // Обновить только статус (для drag & drop)
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateNoteStatus(
            @PathVariable UUID id,
            @RequestBody Map<String, String> payload
    ) {
        String status = payload.get("status");
        System.out.println("PATCH /notes/" + id + "/status: " + status);

        try {
            if (notesStorage.getNoteById(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            notesStorage.updateNoteStatus(id, status);
            return ResponseEntity.ok("Статус обновлен");
        } catch (Exception e) {
            System.err.println("Ошибка при обновлении статуса: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при обновлении статуса");
        }
    }

    // Удалить заметку
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable UUID id) {
        System.out.println("DELETE /notes/" + id);

        try {
            if (notesStorage.getNoteById(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            notesStorage.deleteNote(id);
            return ResponseEntity.ok("Заметка удалена");
        } catch (Exception e) {
            System.err.println("Ошибка при удалении заметки: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при удалении заметки");
        }
    }

    // Получить типы заметок
    @GetMapping("/type")
    public List<NoteType> getType() {
        System.out.println("GET запрос на /notes/type");
        return notesStorage.getType();
    }

    // Получить заметки для календаря
    @GetMapping("/calendar")
    public ResponseEntity<?> getCalendarNotes(@RequestParam String userId) {
        System.out.println("GET /notes/calendar для userId: " + userId);

        try {
            UUID userUUID = UUID.fromString(userId);
            List<Note> calendarNotes = notesStorage.getCalendarNotes(userUUID);
            return ResponseEntity.ok(calendarNotes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Некорректный userId");
        }
    }

    // Получить заметки на конкретную дату
    @GetMapping("/calendar/date")
    public ResponseEntity<?> getNotesByDate(
            @RequestParam String userId,
            @RequestParam String date
    ) {
        System.out.println("GET /notes/calendar/date для userId: " + userId + ", date: " + date);

        try {
            UUID userUUID = UUID.fromString(userId);
            LocalDate localDate = LocalDate.parse(date);
            List<Note> notes = notesStorage.getNotesByDate(userUUID, localDate);
            return ResponseEntity.ok(notes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Некорректные параметры");
        }
    }

    // ========== ПОЛЬЗОВАТЕЛИ ==========

    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            userStorage.addUser(user. getId(), user.getEmail(), user.getPassword());
            return ResponseEntity.ok("Пользователь зарегистрирован");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка регистрации");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        System.out.println("POST /notes/login");

        User found = userStorage.getUser(user.getEmail());

        if (found == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Пользователь не найден");
        }

        if (!found.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Неверный пароль");
        }

        return ResponseEntity.ok(found);
    }
}