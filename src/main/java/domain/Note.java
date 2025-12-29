package domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@Table("notes")
public class Note {

    @Id
    public String id; //uuid
    @Column("data")
    public String text;
    public NoteType type;

    @Column("user_id")
    public UUID userId;

    @PersistenceConstructor
    public Note(String text, NoteType type, UUID userId) {
        this.text = text;
        this.type = type;
        this.userId = userId;

    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", type=" + type +
                ", userId=" + userId +
                '}';
    }
}

// Альтернативный стиль (одним словом, но символично):
//
//Art → Resonance
//
//Body → Vitality
//
//Mind → Sanctum