package by.bsuir.pisl.kp.user;

import by.bsuir.pisl.kp.CustomJFrame;

import by.bsuir.pisl.kp.connection.Connection;
import client.Client;
import com.toedter.calendar.JDateChooser;
import credit.Credit;
import credit.CreditType;
import user.User;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.util.*;

public class CreditWindow extends CustomJFrame {


    private ArrayList<Credit> credits;
    private ArrayList<CreditType> creditTypes;
    private Client client = null;
    private User user = null;
    private JTextField name;
    private JTable creditTable;
    private JButton editButton;
    private JTextField phone;
    private JTextField pasport;
    private JTextField address;
    private JDateChooser birth;
    private JButton saveButton;
    private JComboBox creditType;
    private JTextField percentage;
    private JTextField minTerm;
    private JTextField minSumm;
    private JButton calculateButton;
    private JTextField currency;
    private JButton backButton;
    private JSpinner summ;
    private JSpinner term;
    private JButton doneButton;
    private JButton exitButton;
    private JPanel panel;


    CreditWindow(Client client, User user) {
        super("Кредиты");
        this.client = client;
        this.user = user;
        if(client != null) {
            initClient();
            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    enableFields(true);
                }
            });
        }
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

        creditType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreditType selectedItem = (CreditType) creditType.getSelectedItem();
                minSumm.setText(selectedItem.getMin_summ().toString());
                minTerm.setText(selectedItem.getTerm().toString());
                percentage.setText(selectedItem.getPercentage() + "%");
                currency.setText(selectedItem.getCurrency());
                term.getModel().setValue(Integer.valueOf(minTerm.getText()));
                ((SpinnerNumberModel) term.getModel()).setMinimum(Integer.valueOf(minTerm.getText()));
                summ.getModel().setValue(Double.valueOf(minSumm.getText()));
                ((SpinnerNumberModel) summ.getModel()).setMinimum(Double.valueOf(minSumm.getText()));


            }
        });
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CalculateCreditWindow();
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

        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar calendar = Calendar.getInstance();
                Date startDate = new Date(calendar.getTime().getTime());
                calendar.add(Calendar.MONTH, (Integer) term.getValue());
                Date endDate = new Date(calendar.getTime().getTime());
                Credit credit = new Credit((CreditType) creditType.getSelectedItem(), (Double) summ.getValue(), startDate, endDate, (Integer) term.getValue(), client, user);
                try {
                    Connection.getOutputStream().writeObject(17);
                    Connection.getOutputStream().writeObject(credit);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        initSpinners();
        setPreferredSize(new Dimension(850, 400));
        pack();
        setContentPane(panel);
        setVisible(true);
    }

    private void initSpinners() {
        SpinnerNumberModel summModel = new SpinnerNumberModel(0.0, 0.0, 1000000.0, 100);
        summ.setModel(summModel);
        JSpinner.NumberEditor summEditor = new JSpinner.NumberEditor(summ);
        summ.setEditor(summEditor);
        SpinnerNumberModel termModel = new SpinnerNumberModel(0, 0, 100, 6);
        term.setModel(termModel);
        JSpinner.NumberEditor termEditor = new JSpinner.NumberEditor(term);
        term.setEditor(termEditor);
    }

    private void enableFields(Boolean isEditable) {
        name.setEditable(isEditable);
        pasport.setEditable(isEditable);
        phone.setEditable(isEditable);
        address.setEditable(isEditable);
        birth.setEnabled(isEditable);
        saveButton.setVisible(isEditable);
    }

    private void initClient() {
        name.setText(client.getName());
        name.setEditable(false);
        if (client.getBirth() != null) {
            birth.setDate(client.getBirth());
            birth.setEnabled(false);
        }
        if (client.getPhone() != null && !client.getPhone().isEmpty()) {
            phone.setText(client.getPhone());
            phone.setEditable(false);
        }
        pasport.setText(client.getPassportSeria() + client.getPassportNumber());
        pasport.setEditable(false);
        if (client.getAddress() != null && !client.getAddress().isEmpty()) {
            address.setText(client.getAddress());
            address.setEditable(false);
        }
        editButton.setVisible(true);
    }




    private void createUIComponents() {
        creditTable = new JTable();
        try {
            credits = (ArrayList<Credit>) Connection.getInputStream().readObject();
            Map<Integer, Column> columnMap = new HashMap<Integer, Column>();
            columnMap.put(0, new Column(Integer.class, "ИД", false, 20));
            columnMap.put(1, new Column(String.class, "Кредит", false, 100));
            columnMap.put(2, new Column(String.class, "Валюта", false, 50));
            columnMap.put(3, new Column(String.class, "%", false, 20));
            columnMap.put(4, new Column(Integer.class, "Срок", false, 40));
            columnMap.put(5, new Column(Double.class, "Сумма", false, 40));
            columnMap.put(6, new Column(Date.class, "Открыт", false, 50));
            columnMap.put(7, new Column(Date.class, "Закрытие", false, 50));
            columnMap.put(8, new Column(String.class, "Клиент", false, 50));
            columnMap.put(9, new Column(String.class, "Пользователь", false, 50));
            TableModel tableModel = createTableModel(credits.size(), columnMap);
            creditTable.setModel(tableModel);
            int i = 0;
            for (Credit deposit : credits) {
                creditTable.setValueAt(deposit.getId(), i, 0);
                creditTable.setValueAt(deposit.getCredit().getDescription(), i, 1);
                creditTable.setValueAt(deposit.getCredit().getCurrency(), i, 2);
                creditTable.setValueAt(deposit.getCredit().getPercentage(), i, 3);
                creditTable.setValueAt(deposit.getTerm(), i, 4);
                creditTable.setValueAt(deposit.getSumm(), i, 5);
                creditTable.setValueAt(deposit.getStartDate(), i, 6);
                creditTable.setValueAt(deposit.getEndDate(), i, 7);
                creditTable.setValueAt(deposit.getClient().getName(), i, 8);
                creditTable.setValueAt(deposit.getUser().getName(), i, 9);
                i++;
            }
            setColumnWidth(creditTable, columnMap);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        initComboCredit();

    }

    private void initComboCredit() {
        try {
            creditType = new JComboBox();
            Connection.getOutputStream().writeObject(15);
            creditTypes = (ArrayList<CreditType>) Connection.getInputStream().readObject();
            Vector<CreditType> creditVector = new Vector<>(creditTypes);
            DefaultComboBoxModel<CreditType> comboBoxModel = new DefaultComboBoxModel<CreditType>(creditVector);
            creditType.setModel(comboBoxModel);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}



