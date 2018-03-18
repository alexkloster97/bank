package by.bsuir.pisl.kp.users;

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

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }
    public User() {
        login = "";
        name = "";
    }

    public int getRole_id() {
        return role_id;
    }

    private int id;
    private String login;
    private String name;
    private String password;
    private String role;
    private int role_id;

    public User(int id, String login, String password, String name, int role_id) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.password = password;
        this.role_id = role_id;
    }
    public User(String login, String password, String name, int role_id) {
        this.login = login;
        this.name = name;
        this.password = password;
        this.role_id = role_id;
    }

    public User(int id, String login, String password, String name, String role) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.password = password;
        this.role = role;
    }
    public User(int id, String login, String name, String role) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.role = role;
    }

    public User(String login, String password, String name, String role) {
        this.login = login;
        this.name = name;
        this.password = password;
        this.role = role;
    }
}
