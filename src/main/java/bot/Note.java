package bot;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("notes")
public class Note {

    @Id
    public String id;
    @Column("data")
    public String text;
    public NoteType type;

    public Note(String text) {
        this.text = text;
        this.type = NoteType.ART;

    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", type=" + type +
                '}';
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public NoteType getType() {
        return type;
    }
}
// Альтернативный стиль (одним словом, но символично):
//
//Art → Resonance
//
//Body → Vitality
//
//Mind → Sanctum