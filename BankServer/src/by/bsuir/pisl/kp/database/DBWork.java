package by.bsuir.pisl.kp.database;


import org.apache.log4j.Logger;
import user.Roles;
import user.User;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexk on 05.12.2016.
 */
public class DBWork {
    private static final Logger LOGGER = Logger.getLogger(DBWork.class);

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
                user = new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), Roles.getRoleById(result.getInt(5)), result.getBoolean(6));
            }

        } catch (SQLException e) {
            LOGGER.error("Ошибка авторизации пользователя: " + e.getMessage());
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
                stmt.setInt(4, user.getRole().getId());
                stmt.executeUpdate();
            } catch (SQLException ex) {
                LOGGER.error("Ошибка добавления пользователей: " + ex.getMessage());
                unique = false;
            }
        }
        return unique;
    }

    public static void deleteUsers(List<Integer> users) {

        try {
            for(Integer id: users) {
                stmt = con.prepareStatement("DELETE FROM `bsb_bank`.`user` where `id` = ? ");
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка удаления пользователей: " + e.getMessage());
        }
    }

    private static String getLoginList(List<User> users) {
        StringBuffer str = new StringBuffer();
        String loginList = "";
        for(User user: users) {
            str.append("'");
            str.append(user.getLogin());
            str.append("', ");
        }
        str.setLength(str.length() - 2);
        return str.toString();
    }



    public static ArrayList<User> getAllUsers(Boolean submitted) {

        ArrayList<User> users = new ArrayList<>();
        try {
            if(submitted != null) {
                stmt = con.prepareStatement("SELECT u.id, u.login, u.password, u.name, u.role_id, u.submitted\n" +
                        "FROM bsb_bank.user u\n" +
                        "WHERE u.submitted = ?\n" +
                        "ORDER BY u.id");
                stmt.setBoolean(1, submitted);
            } else {
                stmt = con.prepareStatement("SELECT u.id, u.login, u.password, u.name, u.role_id, u.submitted\n" +
                        "FROM bsb_bank.user u\n" +
                        "ORDER BY u.id");
            }
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                User user = new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), Roles.getRoleById(result.getInt(5)), result.getBoolean(6));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void updateAllUsers(List<User> users) {
        try {
            int i = 0;
            while (i < users.size()) {
                stmt = con.prepareStatement("UPDATE  `bsb_bank`.`user` SET `login`=?, `password`=?, `name`=?, `role_id`=?, `submitted` = ? WHERE `id`=?;");
                stmt.setString(1, users.get(i).getLogin());
                stmt.setString(2, users.get(i).getPassword());
                stmt.setString(3, users.get(i).getName());
                stmt.setInt(4, users.get(i).getRole().getId());
                stmt.setBoolean(5, users.get(i).getSubmitted());
                stmt.setInt(6, users.get(i).getId());
                stmt.executeUpdate();
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
