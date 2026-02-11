package domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Table("notes")
public class Note {

    @Id
    public UUID id;

    @Column("title")
    public String title;

    @Column("data")
    public String text;

    @Column("type")
    public NoteType type;

    @Column("user_id")
    public UUID userId;

    // для канбана
    @Column("status")
    public CanbanType status;
    // для галочки выполнено
    @Column("done")
    public Boolean done;
    // для раздела календаря
    @Column("calendar")
    public Boolean calendar;

    @Column("calendar_date")
    public LocalDate calendarDate;

    @Column("created_at")
    public LocalDate createdAt;

    @PersistenceConstructor
    public Note(UUID id, String title, String text, NoteType type, UUID userId) {
        this.title = title;
        this.text = text;
        this.type = type;
        this.userId = userId;
        this.status = CanbanType.TODO;
        this.done = false;
        this.calendar = false;
        this.calendarDate = null;
        this.createdAt = LocalDate.now();
        this.id = id;

    }

//    @Override
//    public String toString() {
//        return "Note{" +
//                "id='" + id + '\'' +
//                ", title='" + title + '\'' +
//                ", text='" + text + '\'' +
//                ", type=" + type +
//                ", userId=" + userId +
//                '}';
//    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", type=" + type +
                ", userId=" + userId +
                ", status=" + status +
                ", done=" + done +
                ", calendar=" + calendar +
                ", calendarDate=" + calendarDate +
                ", createdAt=" + createdAt +
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