package by.bsuir.pisl.kp.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import by.bsuir.pisl.kp.CustomJFrame;
import by.bsuir.pisl.kp.connection.Connection;
import by.bsuir.pisl.kp.users.User;


/**
 * Created by akloster on 3/5/2018.
 */
public class MainAdmin extends CustomJFrame {
    private JPanel panel;
    private JTable usersTable;
    private JButton requestButton;
    private JButton stattButton;
    private JButton addUserButton;
    private JButton editUserButton;
    private JButton deleteUserButton;
    private JButton backButton;
    private JButton exitButton;
    private JLabel label;
    private ArrayList<User> users;

    public MainAdmin() {
        super("Администрирование");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection.close();
                System.exit(0);
            }
        });
        setPreferredSize(new Dimension(650, 400));
        pack();
        setContentPane(panel);
        setVisible(true);
    }

    private void createUIComponents() {
        try {
            usersTable = new JTable();
            usersTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
            Connection.getOutputStream().writeObject(3);
            users = (ArrayList<User>) Connection.getInputStream().readObject();
            TableModel tableModel = new DefaultTableModel(users.size(), 6) {
                String[] user = {"Номер", "Имя", "Логин", "Пароль", "Роль", "▼"};

                @Override
                public boolean isCellEditable(int row, int column) {
                    return true;
                }
                @Override
                public String getColumnName(int index) {
                    return user[index];
                }


                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    switch (columnIndex) {
                        case 0:
                            return Integer.class;
                        case 1:
                            return String.class;
                        case 2:
                            return String.class;
                        case 3:
                            return String.class;
                        case 4:
                            return String.class;
                        default:
                            return Boolean.class;
                    }
                }
            };
        usersTable.setModel(tableModel);
        int i = 0;
        for(User user: users) {
            usersTable.setValueAt(i+1, i, 0);
            usersTable.setValueAt(user.getName(), i, 1);
            usersTable.setValueAt(user.getLogin(), i, 2);
            usersTable.setValueAt(user.getPassword(), i, 3);
            usersTable.setValueAt(user.getRole(), i, 4);
            usersTable.setValueAt(false, i, 5);
            i++;
        }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
