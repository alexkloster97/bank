package by.bsuir.pisl.kp.admin;

import by.bsuir.pisl.kp.CustomJFrame;
import by.bsuir.pisl.kp.connection.Connection;
import user.Roles;
import user.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRequests extends CustomJFrame {
    private JTable userTable;
    private JButton back;
    private JButton submit;
    private JPanel panel;
    private ArrayList<User> users;

    public UserRequests() {
        super("Запросы на регистрацию");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainAdmin();
                dispose();
            }
        });
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<User> editUserList = new ArrayList<User>();
                for (int i = 0; i < userTable.getRowCount(); i++) {
                    if ((Boolean) userTable.getValueAt(i, 5)) {
                        editUserList.add(new User((Integer) userTable.getValueAt(i, 0),
                                (String) userTable.getValueAt(i, 2), (String) userTable.getValueAt(i, 3),
                                (String) userTable.getValueAt(i, 1), (Roles) userTable.getValueAt(i, 4),
                                true));
                        users.removeAll(editUserList);
                    }
                }
                if (!editUserList.isEmpty()) {
                    try {
                        Connection.getOutputStream().writeObject(5);
                        Connection.getOutputStream().writeObject(editUserList);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                createUIComponents();
            }
        });
        setPreferredSize(new Dimension(650, 400));
        pack();
        setContentPane(panel);
        setVisible(true);
    }

    //HHjkyT:YG1dB
    private void createUIComponents() {
        try {
            userTable = new JTable();
            userTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
            Connection.getOutputStream().writeObject(6);
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
                            return Roles.class;
                        case 5:
                            return Boolean.class;
                        default:
                            return Boolean.class;
                    }
                }
            };
            userTable.setModel(tableModel);
            int i = 0;
            for (User user : users) {
                userTable.setValueAt(user.getId(), i, 0);
                userTable.setValueAt(user.getName(), i, 1);
                userTable.setValueAt(user.getLogin(), i, 2);
                userTable.setValueAt(user.getPassword(), i, 3);
                userTable.setValueAt(user.getRole(), i, 4);
                userTable.setValueAt(false, i, 5);
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

