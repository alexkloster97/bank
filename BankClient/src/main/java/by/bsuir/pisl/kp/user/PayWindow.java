package by.bsuir.pisl.kp.user;

import by.bsuir.pisl.kp.CustomJFrame;
import by.bsuir.pisl.kp.authorization.Authorization;
import by.bsuir.pisl.kp.connection.Connection;
import client.Client;
import payment.Payment;
import user.User;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PayWindow extends CustomJFrame{

    private JTable paymetsTable;
    private JButton payButton;
    private JTextField name;
    private JComboBox paymentType;
    private JButton exitButton;
    private JButton backButton;
    private JPanel clientPanel;
    private JSpinner summ;
    private JTextField birth;
    private JTextField phone;
    private JTextField pasport;
    private JTextField address;
    private JPanel panel;
    private JTextField description;
    private JTextField number;
    private ArrayList<Payment> payments;
    private Client client = null;
    private User user = null;

    PayWindow(Client client, User user) {
        super("БСБ Банк");
        this.client = client;
        this.user = user;
        initFields();
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
            }});


        setPreferredSize(new Dimension(650, 400));
        pack();
        setContentPane(panel);
        setVisible(true);
    }

    private void initFields() {
        name.setText(client.getName());
        name.setEditable(false);
        birth.setText(client.getBirth().toString());
        birth.setEditable(false);
        phone.setText(client.getPhone());
        phone.setEditable(false);
        pasport.setText(client.getPassportSeria() + client.getPassportNumber());
        pasport.setEditable(false);
        address.setText(client.getAddress());
        address.setEditable(false);
    }
    PayWindow(User user) {
        super("БСБ Банк");
        this.user = user;
        createUIComponents();
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
            }});
        setPreferredSize(new Dimension(650, 400));
        pack();
        setContentPane(panel);
        setVisible(true);
    }

    private void createUIComponents() {
        paymetsTable = new JTable();
        try {
            Connection.getOutputStream().writeObject(8);
            Connection.getOutputStream().writeObject(client);
            payments = (ArrayList<Payment>) Connection.getInputStream().readObject();
            Map<Integer, Column> columnMap = new HashMap<Integer, Column>();
            columnMap.put(0, new Column(Integer.class, "ИД", false, 20));
            columnMap.put(1, new Column(String.class, "Тип", false, 100));
            columnMap.put(2, new Column(String.class, "Описание", false, 150));
            columnMap.put(3, new Column(Integer.class, "Номер", false, 100));
            columnMap.put(4, new Column(Double.class, "Cумма", false, 100));
            columnMap.put(5, new Column(String.class, "Клиент", false, 100));
            columnMap.put(6, new Column(String.class, "Операционист", false, 100));

            TableModel tableModel = createTableModel(payments.size(), columnMap);
            paymetsTable.setModel(tableModel);
            int i=0;
            for(Payment payment: payments) {
                paymetsTable.setValueAt(payment.getId(), i, 0);
                paymetsTable.setValueAt(payment.getPayment_type().getType(), i, 1);
                paymetsTable.setValueAt(payment.getDescription(), i, 2);
                paymetsTable.setValueAt(payment.getNumber(), i ,3);
                paymetsTable.setValueAt(payment.getSumm(), i, 4);
                paymetsTable.setValueAt(payment.getClientName(), i, 5);
                paymetsTable.setValueAt(payment.getUserName(), i, 6);
                i++;
            }
            setColumnWidth(paymetsTable, columnMap);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
