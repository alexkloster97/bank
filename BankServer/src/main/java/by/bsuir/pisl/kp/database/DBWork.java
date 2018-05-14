package by.bsuir.pisl.kp.database;


import client.Client;
import credit.Credit;
import credit.CreditType;
import deposit.Deposit;
import deposit.DepositType;
import org.apache.log4j.Logger;

import payment.Payment;
import payment.PaymentType;
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
                stmt = con.prepareStatement("INSERT INTO `bsb_bank`.`user` (`login`,`password`,`name`, `role_id`, submitted) VALUES (?,?,?,?, ?);");
                stmt.setString(1, user.getLogin());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getName());
                stmt.setInt(4, user.getRole().getId());
                stmt.setBoolean(5, user.getSubmitted());
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
            for (Integer id : users) {
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
        for (User user : users) {
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
            if (submitted != null) {
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

    public static List<Client> getAllClients() {
        List<Client> clients = new ArrayList<Client>();
        try {
            stmt = con.prepareStatement("SELECT * FROM bsb_bank.clients ORDER BY id");
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Client client = new Client(result.getInt("id"), result.getString("name"),
                        result.getDate("birth_date"), result.getString("pasport_seria"),
                        result.getInt("pasport_nuber"), result.getString("phone_number"),
                        result.getString("address"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
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

    public static List<Payment> getPaymentsByClientAndUser(Client client, User user) {
        List<Payment> payments = new ArrayList<Payment>();
        try {
            if (client != null) {
                stmt = con.prepareStatement("SELECT\n" +
                        "  p.id        AS payment_id,\n" +
                        "  p.description,\n" +
                        "  p.payment_type_id,\n" +
                        "  p.number,\n" +
                        "  p.summ,\n" +
                        "  p.client_id AS client_id,\n" +
                        "  c.name AS client_name,\n" +
                        "  u.id        AS user_id,\n" +
                        "  u.name      AS user_name\n" +
                        "FROM bsb_bank.payments p\n" +
                        "  JOIN bsb_bank.clients c ON p.client_id = c.id\n" +
                        "  JOIN bsb_bank.user u ON u.id = p.user_id\n" +
                        "WHERE c.id = ?");
                stmt.setInt(1, client.getId());
            } else {
                stmt = con.prepareStatement("SELECT\n" +
                        "  p.id        AS payment_id,\n" +
                        "  p.description,\n" +
                        "  p.payment_type_id,\n" +
                        "  p.number,\n" +
                        "  p.summ,\n" +
                        "  p.client_id AS client_id,\n" +
                        "  c.name AS client_name,\n" +
                        "  u.id        AS user_id,\n" +
                        "  u.name      AS user_name\n" +
                        "FROM bsb_bank.payments p\n" +
                        "  JOIN bsb_bank.clients c ON p.client_id = c.id\n" +
                        "  JOIN bsb_bank.user u ON u.id = p.user_id");
            }
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Payment payment = new Payment(resultSet.getInt("payment_id"), resultSet.getString("description"),
                        PaymentType.getById(resultSet.getInt("payment_type_id")), resultSet.getInt("number"),
                        resultSet.getDouble("summ"), resultSet.getInt("client_id"), resultSet.getString("client_name"),
                        resultSet.getInt("user_id"), resultSet.getString("user_name"));
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public static void addPayment(Payment payment) {
        try {
            stmt = con.prepareStatement("INSERT INTO  payments (description, payment_type_id, number, summ, client_id, user_id) VALUES (?,?,?,?,?,?);");
            stmt.setString(1, payment.getDescription());
            stmt.setInt(2, payment.getPayment_type().getId());
            stmt.setInt(3, payment.getNumber());
            stmt.setDouble(4, payment.getSumm());
            stmt.setInt(5, payment.getClient_id());
            stmt.setInt(6, payment.getUser_id());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Client addClient(Client client) {
        try {
            stmt = con.prepareStatement("insert into clients (name, phone_number, pasport_seria, pasport_nuber, address, birth_date) values (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getPhone());
            stmt.setString(3, client.getPassportSeria());
            stmt.setInt(4, client.getPassportNumber());
            stmt.setString(5, client.getAddress());
            stmt.setDate(6, client.getBirth());
            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
                client.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    public static Client getClientById(Integer id) {
            Client client = null;
            try {
                stmt = con.prepareStatement("SELECT * FROM bsb_bank.clients WHERE id = ?");
                stmt.setInt(1, id);
                ResultSet result = stmt.executeQuery();
                while (result.next()) {
                    client = new Client(result.getInt("id"), result.getString("name"),
                            result.getDate("birth_date"), result.getString("pasport_seria"),
                            result.getInt("pasport_nuber"), result.getString("phone_number"),
                            result.getString("address"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return client;
    }

    public static void updateClient(Client client) {
        try {
            stmt = con.prepareStatement("UPDATE clients SET name = ?, phone_number = ?, pasport_seria = ?, pasport_nuber = ?, address = ?, birth_date = ? WHERE id = ?");
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getPhone());
            stmt.setString(3, client.getPassportSeria());
            stmt.setInt(4, client.getPassportNumber());
            stmt.setString(5, client.getAddress());
            stmt.setDate(6, client.getBirth());
            stmt.setInt(7, client.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<DepositType> getAllDeposits() {
        ArrayList<DepositType> depositTypes = new ArrayList<DepositType>();
        try {
            stmt = con.prepareStatement("SELECT * FROM bsb_bank.deposit_type d JOIN bsb_bank.currency c on d.currency_id = c.id");
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()) {
                DepositType depositType = new DepositType(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getDouble(3), resultSet.getInt(4), resultSet.getDouble(5),
                        resultSet.getInt(6), resultSet.getBoolean(7), resultSet.getString(9));
                depositTypes.add(depositType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return depositTypes;
    }


    public static ArrayList<CreditType> getAllCredits() {
        ArrayList<CreditType> depositTypes = new ArrayList<CreditType>();
        try {
            stmt = con.prepareStatement("SELECT * FROM bsb_bank.credit_type d JOIN bsb_bank.currency c on d.currency_id = c.id");
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()) {
                CreditType depositType = new CreditType(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getDouble(3), resultSet.getInt(4), resultSet.getDouble(5),
                        resultSet.getInt(6), resultSet.getString(8));
                depositTypes.add(depositType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return depositTypes;
    }


    public static CreditType getCreditTypeByID(Integer creditTypeId) {
        CreditType depositType = null;
        try{
            stmt = con.prepareStatement("SELECT * FROM bsb_bank.credit_type d JOIN bsb_bank.currency c on d.currency_id = c.id WHERE d.id = ?");
            stmt.setInt(1, creditTypeId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                depositType = new CreditType(resultSet.getInt("d.id"), resultSet.getString("description"),
                        resultSet.getDouble("percentage"), resultSet.getInt("term"), resultSet.getDouble("min_summ"),
                        resultSet.getInt("c.id"), resultSet.getString("currency"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return depositType;
    }

    public static void addCredit(Credit credit) {
        try {
            stmt = con.prepareStatement("INSERT INTO bsb_bank.credit (credit_id, summ, term, start_date, end_date, user_id, client_id) VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, credit.getCredit().getId());
            stmt.setDouble(2, credit.getSumm());
            stmt.setInt(3, credit.getTerm());
            stmt.setDate(4, credit.getStartDate());
            stmt.setDate(5, credit.getEndDate());
            stmt.setInt(6, credit.getUser().getId());
            stmt.setInt(7, credit.getClient().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Credit> selectCreditOfClient(Client client) {
        ArrayList<Credit> depositList = new ArrayList<Credit>();
        try{
            if(client != null) {
                stmt = con.prepareStatement("SELECT * FROM bsb_bank.credit cr JOIN bsb_bank.credit_type dt ON cr.credit_id = dt.id  WHERE cr.client_id = ?");
                stmt.setInt(1, client.getId());
            } else {
                stmt = con.prepareStatement("SELECT * FROM bsb_bank.credit cr JOIN bsb_bank.credit_type dt ON cr.credit_id = dt.id");
            }
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()) {
                CreditType creditType = getCreditTypeByID(resultSet.getInt("cr.credit_id"));
                User user = getUserById(resultSet.getInt("cr.user_id"));
                client = getClientById(resultSet.getInt("cr.client_id"));
                Credit credit = new Credit(resultSet.getInt("cr.id"), creditType, resultSet.getDouble("cr.summ"),
                        resultSet.getDate("cr.start_date"), resultSet.getDate("cr.end_date"),
                        resultSet.getInt("cr.term"), client, user);
                depositList.add(credit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return depositList;
    }


    public static void addDeposit(Deposit deposit) {
        try {
            stmt = con.prepareStatement("INSERT INTO bsb_bank.deposit (deposit_id, summ, term, start_date, end_date, user_id, client_id) VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, deposit.getDeposit().getId());
            stmt.setDouble(2, deposit.getSumm());
            stmt.setInt(3, deposit.getTerm());
            stmt.setDate(4, deposit.getStartDate());
            stmt.setDate(5, deposit.getEndDate());
            stmt.setInt(6, deposit.getUser().getId());
            stmt.setInt(7, deposit.getClient().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DepositType getDepositTypeById(Integer depositTypeId) {
        DepositType depositType = null;
        try{
            stmt = con.prepareStatement("SELECT * FROM bsb_bank.deposit_type d JOIN bsb_bank.currency c on d.currency_id = c.id WHERE d.id = ?");
            stmt.setInt(1, depositTypeId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                depositType = new DepositType(resultSet.getInt("d.id"), resultSet.getString("description"),
                        resultSet.getDouble("percentage"), resultSet.getInt("term"), resultSet.getDouble("min_summ"),
                        resultSet.getInt("c.id"), resultSet.getBoolean("capitalization"), resultSet.getString("currency"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return depositType;
    }

    public static User getUserById(Integer user_id) {
        User user = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM bsb_bank.user WHERE id = ?");
            stmt.setInt(1, user_id);
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()) {
                user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), Roles.getRoleById(resultSet.getInt(5)), resultSet.getBoolean(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static ArrayList<Deposit> selectDepositsOfClient(Client client) {
        ArrayList<Deposit> depositList = new ArrayList<Deposit>();
        try{
            if(client != null) {
                stmt = con.prepareStatement("SELECT * FROM bsb_bank.deposit d JOIN bsb_bank.deposit_type dt ON d.deposit_id = dt.id  WHERE d.client_id = ?");
                stmt.setInt(1, client.getId());
            } else {
                stmt = con.prepareStatement("SELECT * FROM bsb_bank.deposit d JOIN bsb_bank.deposit_type dt ON d.deposit_id = dt.id");
            }
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()) {
                DepositType depositType = getDepositTypeById(resultSet.getInt("d.deposit_id"));
                client = getClientById(resultSet.getInt("d.client_id"));
                User user = getUserById(resultSet.getInt("d.user_id"));
                Deposit deposit = new Deposit(resultSet.getInt("d.id"), depositType, resultSet.getDouble("d.summ"),
                        resultSet.getDate("d.start_date"), resultSet.getDate("d.end_date"),
                        resultSet.getInt("d.term"), client, user);
                depositList.add(deposit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return depositList;
    }
}
