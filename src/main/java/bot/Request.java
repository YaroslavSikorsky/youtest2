package bot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Request {
    private Long userId;
    private String userName;
    private String number;
    private String tool;
    private String date;
    private String time;
    private Integer messageId;

    @Override
    public String toString() {
        return """
                Имя: %s
                Номер телефона: %s
                Снаряд: %s
                Дата: %s
                Время: %s
                """.formatted(getUserName(), getNumber(), getTool(), getDate(), getTime());
    }
}