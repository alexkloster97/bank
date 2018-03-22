package user;


import java.io.Serializable;

/**
 * Created by alexk on 05.12.2016.
 */
public class User implements Serializable{
    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public Roles getRole() {
        return role;
    }

    public String getName() {
        return name;
    }
    public User() {
        login = "";
        name = "";
    }

    public User(String login, String password, String name, Roles role, Boolean submited) {
        this.login = login;
        this.name = name;
        this.password = password;
        this.role = role;
        this.submitted = submited;
    }

    public Boolean getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Boolean submitted) {
        this.submitted = submitted;
    }

    private int id;
    private String login;
    private String name;
    private String password;
    private Roles role;
    private Boolean submitted = true;

    public User(int id, String login, String password, String name, Roles role, Boolean submitted) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.password = password;
        this.role = role;
        this.submitted = submitted;
    }

    public User(int id, String login, String password, String name, Roles role) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public User(int id, String login, String name, Roles role) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.role = role;
    }
}
