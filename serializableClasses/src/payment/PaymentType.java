package payment;

import java.io.Serializable;

public enum PaymentType implements Serializable{
    PENALTY(8, "Штраф"),
    TICKET(7, "Билеты"),
    HOME(5, "Квартплата"),
    INTERNET(6, "Интернет"),
    OTHER(9, "Другое"),
    PHONE(4, "Телефон");

    private Integer id;
    private String type;

    PaymentType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public static PaymentType getById (Integer id) {
        switch (id) {
            case 4:
                return PHONE;
            case 5:
                return HOME;
            case 6:
                return INTERNET;
            case 7:
                return TICKET;
            case 8:
                return PENALTY;
            default:
                return OTHER;
        }
    }
}
