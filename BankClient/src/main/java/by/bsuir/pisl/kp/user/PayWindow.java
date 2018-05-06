package by.bsuir.pisl.kp.user;

import by.bsuir.pisl.kp.CustomJFrame;
import by.bsuir.pisl.kp.connection.Connection;
import client.Client;
import com.toedter.calendar.JDateChooser;
import payment.Payment;
import payment.PaymentType;
import user.User;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PayWindow extends CustomJFrame {

    private JTable paymetsTable;
    private JButton payButton;
    private JComboBox<PaymentType> paymentType;
    private JButton exitButton;
    private JButton backButton;
    private JPanel clientPanel;
    private JSpinner summ;
    private JPanel panel;
    private JTextField description;
    private JTextField number;
    private ArrayList<Payment> payments;
    protected Client client = null;
    protected User user = null;
    protected JDateChooser birth;
    protected JTextField phone;
    protected JTextField pasport;
    protected JTextField address;
    protected JButton editButton;
    protected JButton save;
    protected JTextField name;


    public PayWindow(Client client, User user) {
        super("БСБ Банк");
        this.client = client;
        this.user = user;
        initFields();
        initClient();
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
        payButton.addActionListener((ActionEvent e) -> {
            if (description.getText().isEmpty() || number.getText().isEmpty()) {
                if (description.getText().isEmpty()) {
                    description.setBorder(BorderFactory.createLineBorder(Color.red));
                }
                if (number.getText().isEmpty()) {
                    number.setBorder(BorderFactory.createLineBorder(Color.red));
                }
                JOptionPane.showMessageDialog(null,
                        "Заполните все поля",
                        "Неверные данные",
                        JOptionPane.WARNING_MESSAGE);
            } else if ((Double) summ.getValue() < 1) {
                summ.setBorder(BorderFactory.createLineBorder(Color.red));
                JOptionPane.showMessageDialog(null,
                        "Сумма не может быть меньше 1 рубля",
                        "Неверные данные",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    Connection.getOutputStream().writeObject(9);
                    Payment payment = new Payment(description.getText(), (PaymentType) paymentType.getSelectedItem(), Integer.valueOf(number.getText()), (Double) summ.getValue(), client, user);
                    Connection.getOutputStream().writeObject(payment);
                    JOptionPane.showMessageDialog(null,
                            "Платеж принят",
                            "Успех", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableFields(true);
            }
        });
        save.addActionListener(new ActionListener() {
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

        setPreferredSize(new Dimension(650, 400));
        pack();
        setContentPane(panel);
        setVisible(true);
    }

    private void initFields() {
        DefaultComboBoxModel<PaymentType> comboBoxModel = new DefaultComboBoxModel<PaymentType>(PaymentType.values());
        paymentType.setModel(comboBoxModel);
        SpinnerNumberModel model = new SpinnerNumberModel(0.0, -1000.0, 1000.0, 0.1);
        summ.setModel(model);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(summ);
        summ.setEditor(editor);
        save.setVisible(false);

    }


    PayWindow(User user) {
        super("БСБ Банк");
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
        editButton.setVisible(false);
        payButton.addActionListener(new ActionListener() {
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
                } else if (description.getText().isEmpty() || number.getText().isEmpty()) {
                    if (description.getText().isEmpty()) {
                        description.setBorder(BorderFactory.createLineBorder(Color.red));
                    }
                    if (number.getText().isEmpty()) {
                        number.setBorder(BorderFactory.createLineBorder(Color.red));
                    }
                    JOptionPane.showMessageDialog(null,
                            "Заполните все поля",
                            "Неверные данные",
                            JOptionPane.WARNING_MESSAGE);
                } else if ((Double) summ.getValue() < 1) {
                    summ.setBorder(BorderFactory.createLineBorder(Color.red));
                    JOptionPane.showMessageDialog(null,
                            "Сумма не может быть меньше 1 рубля",
                            "Неверные данные",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        String seriapasport = pasport.getText().substring(0, 2);
                        Integer numberpasport = Integer.parseInt(pasport.getText().substring(2, pasport.getText().length()));
                        Client client = new Client(name.getText(), new Date(birth.getDate().getTime()), seriapasport, numberpasport, phone.getText(), address.getText());
                        Connection.getOutputStream().writeObject(10);
                        Connection.getOutputStream().writeObject(client);
                        client = (Client) Connection.getInputStream().readObject();
                        Connection.getOutputStream().writeObject(9);
                        Payment payment = new Payment(description.getText(), (PaymentType) paymentType.getSelectedItem(), Integer.valueOf(number.getText()), (Double) summ.getValue(), client, user);
                        Connection.getOutputStream().writeObject(payment);
                        JOptionPane.showMessageDialog(null,
                                "Платеж принят",
                                "Успех", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        initFields();
        setPreferredSize(new Dimension(650, 400));
        pack();
        setContentPane(panel);
        setVisible(true);
    }


    protected void initClient() {
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
    }


    protected void enableFields(Boolean isEditable) {
        name.setEditable(isEditable);
        pasport.setEditable(isEditable);
        phone.setEditable(isEditable);
        address.setEditable(isEditable);
        birth.setEnabled(isEditable);
        save.setVisible(isEditable);
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
            int i = 0;
            for (Payment payment : payments) {
                paymetsTable.setValueAt(payment.getId(), i, 0);
                paymetsTable.setValueAt(payment.getPayment_type().getType(), i, 1);
                paymetsTable.setValueAt(payment.getDescription(), i, 2);
                paymetsTable.setValueAt(payment.getNumber(), i, 3);
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
