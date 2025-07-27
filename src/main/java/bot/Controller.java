package bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/notes")
public class Controller {

    @Autowired
    private RequestStorage requestStorage;

    @GetMapping
    public List<String> getAllRecords() {
        System.out.println("GET запрос на /notes");  // Добавь логирование

        return requestStorage.showNotes();
    }

    @PostMapping
    public void addRecord(@RequestBody String text) {
        System.out.println("POST запрос на /notes с текстом: " + text);  // Логирование

        requestStorage.addNotes(text);
    }
}