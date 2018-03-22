package by.bsuir.pisl.kp.admin;

import javax.management.relation.Role;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import by.bsuir.pisl.kp.CustomJFrame;
import by.bsuir.pisl.kp.authorization.Authorization;
import by.bsuir.pisl.kp.connection.Connection;
import user.Roles;
import user.User;


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
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Authorization();
                dispose();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection.close();
                System.exit(0);
            }
        });
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddUserWindow();
            }
        });
        requestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserRequests();
                dispose();
            }
        });
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> deleteUserList = new ArrayList<Integer>();
                for (int i = 0; i < usersTable.getRowCount(); i++) {
                    if ((Boolean) usersTable.getValueAt(i, 5)) {
                        deleteUserList.add((Integer) usersTable.getValueAt(i, 0));
                    }
                }
                users.removeAll(deleteUserList);
                if (!deleteUserList.isEmpty()) {
                    try {
                        Connection.getOutputStream().writeObject(4);
                        Connection.getOutputStream().writeObject(deleteUserList);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                createUIComponents();
                ((DefaultTableModel)usersTable.getModel()).fireTableDataChanged();
            }
        });
        editUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<User> editUserList = new ArrayList<User>();
                for (int i = 0; i < usersTable.getRowCount(); i++) {
                    if ((Boolean) usersTable.getValueAt(i, 5)) {
                        editUserList.add(new User((Integer) usersTable.getValueAt(i, 0), (String) usersTable.getValueAt(i, 2), (String) usersTable.getValueAt(i, 3), (String) usersTable.getValueAt(i, 1), (Roles) usersTable.getValueAt(i, 4)));
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
                            return Roles.class;
                        case 5:
                            return Boolean.class;
                        default:
                            return Boolean.class;
                    }
                }
            };
            usersTable.setModel(tableModel);
            int i = 0;
            for (User user : users) {
                usersTable.setValueAt(user.getId(), i, 0);
                usersTable.setValueAt(user.getName(), i, 1);
                usersTable.setValueAt(user.getLogin(), i, 2);
                usersTable.setValueAt(user.getPassword(), i, 3);
                usersTable.setValueAt(user.getRole(), i, 4);
                usersTable.setValueAt(false, i, 5);
                i++;
            }
            ;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
