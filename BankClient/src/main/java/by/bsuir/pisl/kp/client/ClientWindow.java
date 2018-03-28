package by.bsuir.pisl.kp.client;

import by.bsuir.pisl.kp.connection.Connection;
import by.bsuir.pisl.kp.user.PayWindow;
import client.Client;
import com.toedter.calendar.JDateChooser;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Vector;

public class ClientWindow extends JFrame {
    private JButton back;
    private JButton save;
    private JTextField name;
    private JTextField pass;
    private JDateChooser birth;
    private JTextField phone;
    private JTextField address;
    private JComboBox clients;
    private JPanel panel;
    private ArrayList<Client> clientsList;

    public ClientWindow(Client client, User user) {
        super("Клиенты");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        initCombo();
        if (client != null) {
            clients.setSelectedItem(client);
            name.setText(client.getName());
            pass.setText(client.getPassportSeria() + client.getPassportNumber());
            birth.setDate(client.getBirth());
            phone.setText(client.getPhone());
            address.setText(client.getAddress());
            save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (name.getText().isEmpty() || pass.getText().isEmpty()) {
                        if (name.getText().isEmpty()) {
                            name.setBorder(BorderFactory.createLineBorder(Color.red));
                        }
                        if (pass.getText().isEmpty()) {
                            pass.setBorder(BorderFactory.createLineBorder(Color.red));
                        }
                        JOptionPane.showMessageDialog(null,
                                "Заполните обязательные поля",
                                "Неверные данные",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        try {
                            String seriaPass = pass.getText().substring(0, 2);
                            Integer numberPass = Integer.parseInt(pass.getText().substring(2, pass.getText().length()));
                            client.setName(name.getText());
                            client.setPassportSeria(seriaPass);
                            client.setPassportNumber(numberPass);
                            client.setBirth(new Date(birth.getDate().getTime()));
                            client.setAddress(address.getText());
                            client.setPhone(phone.getText());
                            Connection.getOutputStream().writeObject(11);
                            Connection.getOutputStream().writeObject(client);
                            dispose();
                            new PayWindow(client, user);

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    }
                }
            });
        } else {
            save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (name.getText().isEmpty() || pass.getText().isEmpty()) {
                        if (name.getText().isEmpty()) {
                            name.setBorder(BorderFactory.createLineBorder(Color.red));
                        }
                        if (pass.getText().isEmpty()) {
                            pass.setBorder(BorderFactory.createLineBorder(Color.red));
                        }
                        JOptionPane.showMessageDialog(null,
                                "Заполните обязательные поля",
                                "Неверные данные",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        try {
                            pass.setBorder(null);
                            name.setBorder(null);
                            String seriaPass = pass.getText().substring(0, 2);
                            Integer numberPass = Integer.parseInt(pass.getText().substring(2, pass.getText().length()));
                            Client client = new Client(name.getText(), new Date(birth.getDate().getTime()), seriaPass, numberPass, phone.getText(), address.getText());
                            Connection.getOutputStream().writeObject(10);
                            Connection.getOutputStream().writeObject(client);
                            client = (Client) Connection.getInputStream().readObject();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (ClassNotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }
            });
        }


        setPreferredSize(new Dimension(250, 250));
        pack();
        setContentPane(panel);
        setVisible(true);
    }

    private void initCombo() {
        try {
            Connection.getOutputStream().writeObject(7);
            clientsList = (ArrayList<Client>) Connection.getInputStream().readObject();
            Vector<Client> clientVector = new Vector<>(clientsList);
            DefaultComboBoxModel<Client> comboBoxModel = new DefaultComboBoxModel<Client>(clientVector);
            clients.setModel(comboBoxModel);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
