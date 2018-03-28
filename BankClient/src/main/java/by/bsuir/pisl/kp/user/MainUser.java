package by.bsuir.pisl.kp.user;

import by.bsuir.pisl.kp.CustomJFrame;
import by.bsuir.pisl.kp.authorization.Authorization;
import by.bsuir.pisl.kp.connection.Connection;
import client.Client;
import user.User;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by akloster on 3/5/2018.
 */
public class MainUser extends CustomJFrame {
    private JButton exchangeButton;
    private JButton creditButton;
    private JButton depositButton;
    private JButton paymentButton;
    private JButton backButton;
    private JButton exitButton;
    private JPanel panel;
    private JPanel backPanel;
    private JTable clientsTable;
    private JScrollPane scrollPane;
    private ArrayList<Client> clients;

    public MainUser(User user) {
        super("БСБ Банк");
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
        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = clientsTable.getSelectedRow();
                if (row == -1) {
                    new PayWindow(user);
                    dispose();
                } else {
                    Client client = null;
                    for (Client cl : clients) {
                        if (cl.getId() == (Integer) clientsTable.getValueAt(row, 0)) {
                            client = cl;
                            break;
                        }
                    }
                    new PayWindow(client, user);
                    dispose();
                }
            }
        });
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = clientsTable.getSelectedRow();
                if (row == -1) {
                    new DepositWindow(user);
                    dispose();
                } else {
                    Client client = null;
                    for (Client cl : clients) {
                        if (cl.getId() == (Integer) clientsTable.getValueAt(row, 0)) {
                            client = cl;
                            break;
                        }
                    }
                    new DepositWindow(client, user);
                    dispose();
                }
            }
        });
        setPreferredSize(new Dimension(650, 400));
        pack();
        setContentPane(panel);
        setVisible(true);
    }

    private void createUIComponents() {
        try {
            clientsTable = new JTable();
            Connection.getOutputStream().writeObject(7);
            clients = (ArrayList<Client>) Connection.getInputStream().readObject();
            Map<Integer, Column> columnMap = new HashMap<Integer, Column>();
            columnMap.put(0, new Column(Integer.class, "ИД", false, 20));
            columnMap.put(1, new Column(String.class, "Имя", false, 100));
            columnMap.put(2, new Column(Date.class, "Дата рождения", false, 50));
            columnMap.put(3, new Column(String.class, "Паспорт", false, 70));
            columnMap.put(4, new Column(String.class, "Телефон", false, 100));
            columnMap.put(5, new Column(String.class, "Адрес", false, 100));
            TableModel tableModel = createTableModel(clients.size(), columnMap);
            clientsTable.setModel(tableModel);
            clientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            int i = 0;
            for (Client client : clients) {
                clientsTable.setValueAt(client.getId(), i, 0);
                clientsTable.setValueAt(client.getName(), i, 1);
                clientsTable.setValueAt(client.getBirth(), i, 2);
                clientsTable.setValueAt(client.getPassportSeria() + client.getPassportNumber(), i, 3);
                clientsTable.setValueAt(client.getPhone(), i, 4);
                clientsTable.setValueAt(client.getAddress(), i, 5);
                i++;
            }
            setColumnWidth(clientsTable, columnMap);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
