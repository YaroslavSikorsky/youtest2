package domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Table("users")
public class User {
    @Id
    private UUID id;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    @Column("email")
    private String email;
    @Column("password")
    private String password;
    @Column("date_birthday")
    private LocalDate dateBirthday;
    @Column("id_note_list")
    private UUID idNoteList;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("subscription")
    private Boolean subscription;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.subscription = true;
        this.id = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateBirthday='" + dateBirthday + '\'' +
                ", idNoteList='" + idNoteList + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
