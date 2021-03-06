package by.bsuir.pisl.kp.connection;

import by.bsuir.pisl.kp.database.DBWork;
import client.Client;
import credit.Credit;
import deposit.Deposit;
import org.apache.log4j.Logger;
import payment.Payment;
import user.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Created by akloster on 3/5/2018.
 */
public class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class);
    private List<Connection> connections =
            Collections.synchronizedList(new ArrayList<Connection>());
    private ServerSocket server;

    public Server() {
        try {
            server = new ServerSocket(1111);

            while (true) {
                Socket socket = server.accept();
                if(server.isClosed())
                    break;
                Connection con = new Connection(socket);
                connections.add(con);
                con.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeAll() {
        try {
            server.close();
            synchronized (connections) {
                Iterator<Connection> iter = connections.iterator();
                while (iter.hasNext()) {
                    ((Connection) iter.next()).close();
                }
            }
        } catch (Exception e) {
            System.err.println("Потоки не были закрыты!");
        }
    }

    private class Connection extends Thread {
        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        private String login;
        private String password;
        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        private List<String> users = new ArrayList<>();

        public Connection(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                Boolean connected = true;
                User user = new User();
                Client client = null;
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                synchronized (connections) {
                    Iterator<Connection> iter = connections.iterator();
                    while (iter.hasNext()) {
                        users.add(iter.next().login);
                    }
                }
                while(connected) {
                    switch ((Integer)in.readObject()) {
                        case 1://авторизация
                            login = (String) in.readObject();
                            LOGGER.info("Попытка авторизации. Пользователь: " + login);
                            password = (String) in.readObject();
                            user = DBWork.authorisation(login, password);
                            if(user.getLogin().isEmpty()) {
                                LOGGER.info("Неверные учетные данные. Пользователь: " + login);
                            } else if (!user.getSubmitted()) {
                                LOGGER.info("Пользователь не подтвержден администратором");
                            } else {
                                LOGGER.info("Авторизация успешна");
                            }
                            out.writeObject(user);
                            break;
                        case 2://регистрация
                            user = (User) in.readObject();
                            out.writeObject(DBWork.registration(user));
                            LOGGER.info("Регистрация пользователя: " + user.getLogin());
                            break;
                        case 3:
                            //чтение всех пользователей
                            LOGGER.info("Список пользовтелей отправлен клиенту: " + this.getLogin());
                            out.writeObject(DBWork.getAllUsers(true));
                            break;
                        case 4:
                            LOGGER.info("Удаление пользователей клиентом:" + this.getLogin());
                            List<Integer> deleteUserList = (List<Integer>) in.readObject();
                            DBWork.deleteUsers(deleteUserList);
                            break;
                        case 5:
                            LOGGER.info("Редактирование пользователей клиентом:" + this.getLogin());
                            List<User> editUserList = (List<User>) in.readObject();
                            DBWork.updateAllUsers(editUserList);
                            break;
                        case 6:
                            LOGGER.info("Подтверждение запросов пользователей клиентом:" + this.getLogin());
                            out.writeObject(DBWork.getAllUsers(false));
                            break;
                        case 7:
                            LOGGER.info("Список клиентов банка отправлен пользователю:" + this.getLogin());
                            out.writeObject(DBWork.getAllClients());
                            break;
                        case 8:
                            client = (Client) in.readObject();
                            out.writeObject(DBWork.getPaymentsByClientAndUser(client, user));
                            LOGGER.info("Список платежей отправлен пользователю:" + this.getLogin());
                            break;
                        case 9:
                            Payment payment = (Payment)in.readObject();
                            DBWork.addPayment(payment);
                            LOGGER.info("Платеж  " + payment.getDescription() + " клиента " + payment.getClientName() + "принят пользователем:" + this.getLogin());
                            break;
                        case 10:
                            client = (Client) in.readObject();
                            client = DBWork.addClient(client);
                            out.writeObject(client);
                            LOGGER.info("Клиент " + client.getName() +" добавлен пользователем: " + this.getLogin());
                            break;
                        case 11:
                            client = (Client) in.readObject();
                            DBWork.updateClient(client);
                            LOGGER.info("Клиент " + client.getName() +" обновлен пользователем: " + this.getLogin());
                            break;
                        case 12:
                            out.writeObject(DBWork.getAllDeposits());
                            LOGGER.info("Список вкладов отправлен пользователю:" + this.getLogin());
                            break;
                        case 13:
                            Deposit deposit = (Deposit)in.readObject();
                            DBWork.addDeposit(deposit);
                            LOGGER.info("Вклад " + deposit.getDeposit().getDescription() + " клиента " + deposit.getClient().getName() + " принят пользователем:" + this.getLogin());
                            break;
                        case 14:
                            client = (Client) in.readObject();
                            ArrayList<Deposit> deposits = DBWork.selectDepositsOfClient(client);
                            out.writeObject(deposits);
                            break;
                        case 15:
                            out.writeObject(DBWork.getAllCredits());
                            LOGGER.info("Список кредитов отправлен пользователю:" + this.getLogin());
                            break;
                        case 16:
                            client = (Client) in.readObject();
                            ArrayList<Credit> credits = DBWork.selectCreditOfClient(client);
                            out.writeObject(credits);
                            break;
                        case 17:
                            Credit credit = (Credit) in.readObject();
                            DBWork.addCredit(credit);
                            LOGGER.info("Вклад " + credit.getCredit().getDescription() + " клиента " + credit.getClient().getName() + " принят пользователем:" + this.getLogin());
                            break;
                        case -1:
                            connected = false;
                            break;
                    }
                }
                close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public void close() {
            try {
                in.close();
                out.close();
                socket.close();
                connections.remove(this);
                LOGGER.info("Пользователь " + this.getLogin() + " завершил работу.");
            } catch (Exception e) {
                System.err.println("Потоки не были закрыты!");
            }
        }
    }
}


