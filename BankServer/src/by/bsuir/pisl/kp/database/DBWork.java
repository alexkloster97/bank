package by.bsuir.pisl.kp.database;


import by.bsuir.pisl.kp.users.User;


import java.sql.*;
import java.util.ArrayList;

/**
 * Created by alexk on 05.12.2016.
 */
public class DBWork {
    private static PreparedStatement stmt;
    private static Connection con = DBConnection.getConnection();

    public static User authorisation(String login, String password) {
        User user = new User();
        try {
            stmt = con.prepareStatement("SELECT * FROM `bsb_bank`.`user` where `login` LIKE ? AND `password` like ?");
            stmt.setString(1, login);
            stmt.setString(2, password);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                user = new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getInt(5));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static boolean registration(User user) {
        boolean unique = true;
        try {
            stmt = con.prepareStatement("SELECT count(*) FROM `bsb_bank`.`user` where `login` LIKE ? ");
            stmt.setString(1, user.getLogin());
            ResultSet result = stmt.executeQuery();
            result.next();
            if (result.getInt(1) != 0)
                unique = false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (unique) {
            try {
                stmt = con.prepareStatement("INSERT INTO `bsb_bank`.`user` (`login`,`password`,`name`, `role_id`) VALUES (?,?,?,?);");
                stmt.setString(1, user.getLogin());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getName());
                stmt.setInt(4, user.getRole_id());
                stmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
                unique = false;
            }
        }
        return unique;
    }

    public static ArrayList<User> getAllUsers() {

        ArrayList<User> users = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT u.id, u.login, u.password, u.name, r.name AS role\n" +
                    "FROM bsb_bank.user u INNER JOIN bsb_bank.role r ON u.role_id = r.id\n" +
                    "ORDER BY u.id");
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                User user = new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void updateAllUsers(ArrayList<User> users) {
        try {
            int i = 0;
            while (i < users.size()) {
                stmt = con.prepareStatement("UPDATE  `bsb_bank`.`user` SET `login`=?, `password`=?, `name`=?, `role_id`=? WHERE `id`=?;");
                stmt.setString(1, users.get(i).getLogin());
                stmt.setString(2, users.get(i).getPassword());
                stmt.setString(3, users.get(i).getName());
                stmt.setInt(4, users.get(i).getRole_id());
                stmt.setInt(5, users.get(i).getId());
                stmt.executeUpdate();
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
