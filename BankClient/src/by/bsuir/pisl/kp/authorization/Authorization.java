package by.bsuir.pisl.kp.authorization;

import by.bsuir.pisl.kp.CustomJFrame;
import by.bsuir.pisl.kp.admin.MainAdmin;
import by.bsuir.pisl.kp.user.MainUser;
import by.bsuir.pisl.kp.connection.Connection;
import user.Roles;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by alexk on 05.12.2016.
 */
public class Authorization extends CustomJFrame {
    private JPanel panel;
    private JTextField login;
    private JPasswordField password;
    private JButton registration;
    private JButton enter;

    public Authorization() {
        super("Авторизация");
        registration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Registration();
                dispose();
            }
        });
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (login.getText().isEmpty() || String.valueOf(password.getPassword()).isEmpty()) {
                    if (login.getText().isEmpty()) {
                        login.setBorder(BorderFactory.createLineBorder(Color.red));
                    }
                    if (password.getText().isEmpty()) {
                        password.setBorder(BorderFactory.createLineBorder(Color.red));
                    }
                    JOptionPane.showMessageDialog(null,
                            "Введите корректные данные",
                            "Неверные данные",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        login.setBorder(null);
                        password.setBorder(null);
                        Connection.getOutputStream().writeObject(1);
                        Connection.getOutputStream().writeObject(login.getText());
                        Connection.getOutputStream().writeObject(String.valueOf(password.getPassword()));
                        User user = (User) Connection.getInputStream().readObject();
                        if (user.getLogin().isEmpty()) {
                            login.setBorder(BorderFactory.createLineBorder(Color.red));
                            password.setBorder(BorderFactory.createLineBorder(Color.red));
                            JOptionPane.showMessageDialog(null,
                                    "Введите корректные данные",
                                    "Неверные данные",
                                    JOptionPane.WARNING_MESSAGE);
                        } else if (!user.getSubmitted()) {
                            JOptionPane.showMessageDialog(null,
                                    "Пользователь " + user.getName() + " не подтвержден админимтратором",
                                    "Отказно.",
                                    JOptionPane.ERROR_MESSAGE);

                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Добро пожаловать, " + user.getName(),
                                    "Добро пожаловать!",
                                    JOptionPane.INFORMATION_MESSAGE);
                            if (user.getRole().equals(Roles.ADMINISTRATOR)) {
                                new MainAdmin();
                            } else if (user.getRole().equals(Roles.USER)) {
                                new MainUser();
                            }
                            dispose();
                        }

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });

        setPreferredSize(new Dimension(250,250));
        pack();
        setContentPane(panel);
        setVisible(true);
    }
}
