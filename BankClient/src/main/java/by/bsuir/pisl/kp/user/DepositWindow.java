package by.bsuir.pisl.kp.user;

import by.bsuir.pisl.kp.CustomJFrame;
import by.bsuir.pisl.kp.connection.Connection;
import client.Client;
import com.toedter.calendar.JDateChooser;
import deposit.Deposit;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Vector;

public class DepositWindow extends CustomJFrame {
    private JTable deposTable;
    private JButton editButton;
    private JTextField name;
    private JDateChooser birth;
    private JTextField phone;
    private JTextField pasport;
    private JTextField address;
    private JComboBox depos;
    private JButton saveButton;
    private JPanel panel;
    private JButton backButton;
    private JButton exitButton;
    private JSpinner summ;
    private JSpinner term;
    private JButton doneButton;
    private JTextField percentage;
    private JTextField minTerm;
    private JTextField minSumm;
    private JCheckBox kapitalization;
    private JTextField currency;
    private JButton calculateButton;
    protected Client client = null;
    protected User user = null;
    private ArrayList<Deposit> deposits = new ArrayList<Deposit>();


    public DepositWindow(Client client, User user) {
        super("Вклады");
        this.user = user;
        this.client = client;
        initClient();
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableFields(true);
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (name.getText().isEmpty() || pasport.getText().isEmpty()) {
                    if (name.getText().isEmpty()) {
                        name.setBorder(BorderFactory.createLineBorder(Color.red));
                    }
                    if (pasport.getText().isEmpty()) {
                        pasport.setBorder(BorderFactory.createLineBorder(Color.red));
                    }
                    JOptionPane.showMessageDialog(null,
                            "Заполните обязательные поля",
                            "Неверные данные",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        SwingUtilities.updateComponentTreeUI(pasport);
                        SwingUtilities.updateComponentTreeUI(name);
                        String seriaPass = pasport.getText().substring(0, 2);
                        Integer numberPass = Integer.parseInt(pasport.getText().substring(2, pasport.getText().length()));
                        client.setName(name.getText());
                        client.setPassportSeria(seriaPass);
                        client.setPassportNumber(numberPass);
                        client.setBirth(new Date(birth.getDate().getTime()));
                        client.setAddress(address.getText());
                        client.setPhone(phone.getText());
                        Connection.getOutputStream().writeObject(11);
                        Connection.getOutputStream().writeObject(client);
                        enableFields(false);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainUser(user);
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
        setPreferredSize(new Dimension(650, 400));
        pack();
        setContentPane(panel);
        setVisible(true);
    }

    public DepositWindow(User user) {
        super("Вклады");
        this.user = user;
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainUser(user);
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
        setPreferredSize(new Dimension(650, 400));
        pack();
        setContentPane(panel);
        setVisible(true);
    }

    protected void initClient() {
        name.setText(client.getName());
        name.setEditable(false);
        if(client.getBirth() != null) {
            birth.setDate(client.getBirth());
            birth.setEnabled(false);
        }
        if(client.getPhone() != null && !client.getPhone().isEmpty()) {
            phone.setText(client.getPhone());
            phone.setEditable(false);
        }
        pasport.setText(client.getPassportSeria() + client.getPassportNumber());
        pasport.setEditable(false);
        if(client.getAddress() != null && !client.getAddress().isEmpty()) {
            address.setText(client.getAddress());
            address.setEditable(false);
        }
        editButton.setVisible(true);
    }



    private void enableFields(Boolean isEditable) {
        name.setEditable(isEditable);
        pasport.setEditable(isEditable);
        phone.setEditable(isEditable);
        address.setEditable(isEditable);
        birth.setEnabled(isEditable);
        saveButton.setVisible(isEditable);
    }

    private void initComboAndDeposit () {
        try {
            depos = new JComboBox();
            Connection.getOutputStream().writeObject(12);
            deposits = (ArrayList<Deposit>) Connection.getInputStream().readObject();
            Vector<Deposit> depositsVector = new Vector<>(deposits);
            DefaultComboBoxModel<Deposit> comboBoxModel = new DefaultComboBoxModel<Deposit>(depositsVector);
            depos.setModel(comboBoxModel);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void createUIComponents() {
        initComboAndDeposit();
        depos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Deposit deposit = (Deposit) depos.getSelectedItem();
                minSumm.setText(deposit.getMin_summ().toString());
                minTerm.setText(deposit.getTerm().toString());
                percentage.setText(deposit.getPercentage() + "%");
                kapitalization.setSelected(deposit.getCapitalization());
                currency.setText(deposit.getCurrency());
            }
        });
    }
}
