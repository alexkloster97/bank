package by.bsuir.pisl.kp.user;

import by.bsuir.pisl.kp.CustomJFrame;
import by.bsuir.pisl.kp.connection.Connection;
import client.Client;
import com.toedter.calendar.JDateChooser;
import deposit.Deposit;
import deposit.DepositType;
import org.jcp.xml.dsig.internal.dom.DOMUtils;
import user.User;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

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
    private ArrayList<DepositType> depositTypes = new ArrayList<DepositType>();
    private ArrayList<Deposit> deposits = new ArrayList<Deposit>();

    public DepositWindow(Client client, User user) {
        super("Вклады");
        this.client = client;
        this.user = user;
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
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CalculateWindow();
            }
        });
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar calendar = Calendar.getInstance();
                Date startDate = new Date(calendar.getTime().getTime());
                calendar.add(Calendar.MONTH, (Integer) term.getValue());
                Date endDate = new Date(calendar.getTime().getTime());
                Deposit deposit = new Deposit((DepositType) depos.getSelectedItem(), (Double) summ.getValue(), startDate, endDate, (Integer) term.getValue(), client, user);
                try {
                    Connection.getOutputStream().writeObject(13);
                    Connection.getOutputStream().writeObject(deposit);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        depos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DepositType depositType = (DepositType) depos.getSelectedItem();
                minSumm.setText(depositType.getMin_summ().toString());
                minTerm.setText(depositType.getTerm().toString());
                percentage.setText(depositType.getPercentage() + "%");
                kapitalization.setSelected(depositType.getCapitalization());
                currency.setText(depositType.getCurrency());
                term.getModel().setValue(Integer.valueOf(minTerm.getText()));
                ((SpinnerNumberModel) term.getModel()).setMinimum(Integer.valueOf(minTerm.getText()));
                summ.getModel().setValue(Double.valueOf(minSumm.getText()));
                ((SpinnerNumberModel) summ.getModel()).setMinimum(Double.valueOf(minSumm.getText()));
            }
        });
        initSpinners();
        setPreferredSize(new Dimension(850, 400));
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
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar calendar = Calendar.getInstance();
                Date startDate = new Date(calendar.getTime().getTime());
                calendar.add(Calendar.MONTH, (Integer) term.getValue());
                Date endDate = new Date(calendar.getTime().getTime());
                Deposit deposit = new Deposit((DepositType) depos.getSelectedItem(), (Double) summ.getValue(), startDate, endDate, (Integer) term.getValue(), client, user);
                try {
                    Connection.getOutputStream().writeObject(13);
                    Connection.getOutputStream().writeObject(deposit);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        depos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DepositType depositType = (DepositType) depos.getSelectedItem();
                minSumm.setText(depositType.getMin_summ().toString());
                minTerm.setText(depositType.getTerm().toString());
                percentage.setText(depositType.getPercentage() + "%");
                kapitalization.setSelected(depositType.getCapitalization());
                currency.setText(depositType.getCurrency());
                term.getModel().setValue(Integer.valueOf(minTerm.getText()));
                ((SpinnerNumberModel) term.getModel()).setMinimum(Integer.valueOf(minTerm.getText()));
                summ.getModel().setValue(Double.valueOf(minSumm.getText()));
                ((SpinnerNumberModel) summ.getModel()).setMinimum(Double.valueOf(minSumm.getText()));
            }
        });
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CalculateWindow();
            }
        });
        initSpinners();
        setPreferredSize(new Dimension(850, 400));
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
        editButton.setVisible(true);
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

    private void initComboAndDeposit() {
        try {
            depos = new JComboBox();
            Connection.getOutputStream().writeObject(12);
            depositTypes = (ArrayList<DepositType>) Connection.getInputStream().readObject();
            Vector<DepositType> depositsVector = new Vector<>(depositTypes);
            DefaultComboBoxModel<DepositType> comboBoxModel = new DefaultComboBoxModel<DepositType>(depositsVector);
            depos.setModel(comboBoxModel);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void createUIComponents() {
        deposTable = new JTable();
        try {
            deposits = (ArrayList<Deposit>) Connection.getInputStream().readObject();
            Map<Integer, Column> columnMap = new HashMap<Integer, Column>();
            columnMap.put(0, new Column(Integer.class, "ИД", false, 20));
            columnMap.put(1, new Column(String.class, "Вклад", false, 100));
            columnMap.put(2, new Column(String.class, "Валюта", false, 50));
            columnMap.put(3, new Column(String.class, "%", false, 20));
            columnMap.put(4, new Column(Integer.class, "Срок", false, 40));
            columnMap.put(5, new Column(Double.class, "Сумма", false, 40));
            columnMap.put(6, new Column(Date.class, "Открыт", false, 50));
            columnMap.put(7, new Column(Date.class, "Закрытие", false, 50));
            columnMap.put(8, new Column(Double.class, "Накоплено", false, 50));
            columnMap.put(9, new Column(String.class, "Клиент", false, 50));
            columnMap.put(10, new Column(String.class, "Пользователь", false, 50));
            TableModel tableModel = createTableModel(deposits.size(), columnMap);
            deposTable.setModel(tableModel);
            int i = 0;
            for (Deposit deposit : deposits) {
                deposTable.setValueAt(deposit.getId(), i, 0);
                deposTable.setValueAt(deposit.getDeposit().getDescription(), i, 1);
                deposTable.setValueAt(deposit.getDeposit().getCurrency(), i, 2);
                deposTable.setValueAt(deposit.getDeposit().getPercentage(), i, 3);
                deposTable.setValueAt(deposit.getTerm(), i, 4);
                deposTable.setValueAt(deposit.getSumm(), i, 5);
                deposTable.setValueAt(deposit.getStartDate(), i, 6);
                deposTable.setValueAt(deposit.getEndDate(), i, 7);
                deposTable.setValueAt(calculataProfitForCurrentDate(deposit), i, 8);
                deposTable.setValueAt(deposit.getClient().getName(), i, 9);
                deposTable.setValueAt(deposit.getUser().getName(), i, 10);
                i++;
            }
            setColumnWidth(deposTable, columnMap);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        initComboAndDeposit();

    }

    private Double calculataProfitForCurrentDate(Deposit deposit) {
        Double profit = null;
        Double summ = deposit.getSumm();
        Double percentage = deposit.getDeposit().getPercentage()/100;
        LocalDateTime startDeposDate = LocalDateTime.of(deposit.getStartDate().toLocalDate(), LocalTime.MIDNIGHT);
        LocalDateTime endDeposDate = LocalDateTime.of(deposit.getEndDate().toLocalDate(), LocalTime.MIDNIGHT);
        LocalDateTime currentDate = LocalDateTime.now();
        if(deposit.getDeposit().getCapitalization()) {
            LocalDateTime bankStartDate = LocalDateTime.of(2016, 1, 1, 0, 0, 0);
            while (bankStartDate.isBefore(startDeposDate)) {
                bankStartDate = bankStartDate.plusMonths(3);
            }
            if (bankStartDate.isBefore(currentDate) || bankStartDate.isEqual(currentDate)) {
                long daysStart = Math.abs(Duration.between(startDeposDate, bankStartDate).toDays());
                int n = 0;
                if (currentDate.isAfter(endDeposDate)) {
                    currentDate = endDeposDate;
                }
                while (bankStartDate.isBefore(currentDate.minusMonths(3))) {
                    bankStartDate = bankStartDate.plusMonths(3);
                    n++;
                }
                long daysEnd = Duration.between(bankStartDate, currentDate).toDays();
                Double startSum = summ * daysStart * (percentage / 365);
                Double midleSumm = (summ + startSum) * (Math.pow((1 + percentage / 4), n));
                Double endSumm = midleSumm * daysEnd * percentage / 365;
                endSumm = midleSumm + endSumm;
                profit = endSumm - summ;
            } else {
                long daysStart = Math.abs(Duration.between(startDeposDate, currentDate).toDays());
                profit = summ * daysStart * (percentage / 365);
                ;
            }
        } else {
            if(endDeposDate.isBefore(currentDate)) {
                currentDate = endDeposDate;
            }
            long days = Math.abs(Duration.between(startDeposDate, currentDate).toDays());
            Double endSum = summ * days * (percentage/365);
            profit = endSum - summ;
        }
        return profit;
    }

    private Instant sqlDateToInstant(Date date) {
        LocalDate calendarDate = date.toLocalDate();
        ZonedDateTime zdt = calendarDate.atStartOfDay(ZoneId.of("Europe/Minsk"));
        return zdt.toInstant();
    }
}
