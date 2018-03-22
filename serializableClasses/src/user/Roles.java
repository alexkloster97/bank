package user;

import java.io.Serializable;

public enum Roles implements Serializable{
    ADMINISTRATOR ("Администратор", 1),
    USER ("Пользователь", 2);

    private String role;
    private Integer id;

    Roles(String role, Integer id) {
        this.role = role;
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public Integer getId() {
        return id;
    }

    public static final Roles getRoleById (Integer id) {
        if(id == 1)
            return ADMINISTRATOR;
        else
            return USER;
    }

    @Override
    public String toString() {
        return role;
    }
}
