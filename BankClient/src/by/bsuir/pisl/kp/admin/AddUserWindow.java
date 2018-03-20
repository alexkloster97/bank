package by.bsuir.pisl.kp.admin;

import by.bsuir.pisl.kp.connection.Connection;
import user.Roles;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class AddUserWindow extends  JFrame{
    private JTextField login;
    private JPasswordField password;
    private JTextField name;
    private JComboBox role;
    private JButton back;
    private JButton add;
    private JPanel panel;
    private User user;

    public AddUserWindow() {
        super("Регистрация");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login.setBorder(null);
                password.setBorder(null);
                name.setBorder(null);
                if(login.getText().isEmpty()||password.getText().isEmpty()||name.getText().isEmpty()){
                    if(login.getText().isEmpty())
                        login.setBorder(BorderFactory.createLineBorder(Color.red));
                    if(password.getText().isEmpty())
                        password.setBorder(BorderFactory.createLineBorder(Color.red));
                    if(name.getText().isEmpty())
                        name.setBorder(BorderFactory.createLineBorder(Color.red));
                    JOptionPane.showMessageDialog(null,
                            "Заполните все поля!",
                            "Ошибка",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    switch((String)role.getSelectedItem()) {
                        case "Администратор":
                            user = new User(login.getText(), password.getText(), name.getText(), Roles.getRoleById(1), true);
                            break;
                        case "Пользователь":
                            user = new User(login.getText(), password.getText(), name.getText(), Roles.getRoleById(2), true);
                            break;
                    }
                    try {
                        Connection.getOutputStream().writeObject(2);
                        Connection.getOutputStream().writeObject(user);
                        if ((Boolean)Connection.getInputStream().readObject()) {
                            Connection.getOutputStream().writeObject(3);
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

        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection.getOutputStream().writeObject(3);
                    new MainAdmin();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                dispose();
            }
        });
        setPreferredSize(new Dimension(250,300));
        pack();
        setContentPane(panel);
        setVisible(true);
    }
}
