package by.bsuir.pisl.kp.connection;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * Created by alexk on 28.11.2016.
 */
public class Connection {
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static Socket socket;
    private static final Connection servConnection = new Connection();
    private Connection () {
        try {
            socket = new Socket("127.0.0.1", 1111);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Ошибка сервера",
                    "Неудалось подключиться",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    public static ObjectInputStream getInputStream () {
        return servConnection.in;
    }
    public static ObjectOutputStream getOutputStream () {
        return servConnection.out;
    }
    public static void close() {
        try {
            servConnection.out.writeObject(-1);
            servConnection.in.close();
            servConnection.out.close();
            servConnection.socket.close();
        } catch (Exception e) {
            System.err.println("Потоки не были закрыты!");
        }
    }
}
