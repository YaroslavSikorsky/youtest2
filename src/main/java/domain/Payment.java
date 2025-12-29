package domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
public class Payment {

    private UUID id;
    private UUID userId;
    private Integer sale;
    private Timestamp datePayment;

    public Payment(UUID id, UUID userId, Integer sale, Timestamp datePayment) {
        this.id = id;
        this.userId = userId;
        this.sale = sale;
        this.datePayment = datePayment;
    }


    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", userId=" + userId +
                ", sale=" + sale +
                ", datePayment=" + datePayment +
                '}';
    }
}
