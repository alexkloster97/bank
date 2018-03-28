package by.bsuir.pisl.kp.authorization;

import by.bsuir.pisl.kp.CustomJFrame;
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
public class Registration extends CustomJFrame {
    private JPanel panel;
    private JTextField login;
    private JPasswordField password_1;
    private JPasswordField password_2;
    private JButton register;
    private JTextField name;
    private User user = new User();

    public Registration() {
        super("Регистрация");
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login.setBorder(null);
                password_2.setBorder(null);
                password_1.setBorder(null);
                name.setBorder(null);
                if (login.getText().isEmpty() || password_1.getText().isEmpty() || password_2.getText().isEmpty() || name.getText().isEmpty()) {
                    if (login.getText().isEmpty())
                        login.setBorder(BorderFactory.createLineBorder(Color.red));
                    if (password_1.getText().isEmpty())
                        password_1.setBorder(BorderFactory.createLineBorder(Color.red));
                    if (password_2.getText().isEmpty())
                        password_2.setBorder(BorderFactory.createLineBorder(Color.red));
                    if (name.getText().isEmpty())
                        name.setBorder(BorderFactory.createLineBorder(Color.red));
                    JOptionPane.showMessageDialog(null,
                            "Заполните все поля!",
                            "Ошибка",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    if (!password_1.getText().equals(password_2.getText())) {
                        password_1.setBorder(BorderFactory.createLineBorder(Color.red));
                        password_2.setBorder(BorderFactory.createLineBorder(Color.red));
                        JOptionPane.showMessageDialog(null,
                                "Пароли не совпадают",
                                "Ошибка",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        user = new User(login.getText(), password_1.getText(), name.getText(), Roles.USER, false);
                        try {
                            Connection.getOutputStream().writeObject(2);
                            Connection.getOutputStream().writeObject(user);
                            if ((Boolean) Connection.getInputStream().readObject()) {
                                new Authorization();
                                dispose();
                            } else {
                                login.setBorder(BorderFactory.createLineBorder(Color.red));
                                JOptionPane.showMessageDialog(null,
                                        "Введенный логин занят!",
                                        "Ошибка",
                                        JOptionPane.WARNING_MESSAGE);
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (ClassNotFoundException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        setPreferredSize(new Dimension(250, 270));
        pack();
        setContentPane(panel);
        setVisible(true);
    }

}
