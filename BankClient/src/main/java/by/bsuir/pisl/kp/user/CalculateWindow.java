package by.bsuir.pisl.kp.user;

import by.bsuir.pisl.kp.connection.Connection;
import deposit.DepositType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Vector;

public class CalculateWindow extends JFrame {
    private JPanel panel;
    private JComboBox deposit;
    private JTextField percentage;
    private JTextField minTerm;
    private JTextField minSumm;
    private JCheckBox capitalization;
    private JTextField currency;
    private JSpinner term;
    private JSpinner summ;
    private JTextField endSumm;
    private JTextField profit;
    private JButton calculateButton;
    private ArrayList<DepositType> depositTypes = new ArrayList<DepositType>();
    private Double resultValue;
    private Double profitValue;


    public CalculateWindow(JComboBox deposit1) {
        super("Рассчет");
        initSpinners();
        deposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DepositType depositTypeEntity = (DepositType) deposit.getSelectedItem();
                minSumm.setText(depositTypeEntity.getMin_summ().toString());
                minTerm.setText(depositTypeEntity.getTerm().toString());
                percentage.setText(depositTypeEntity.getPercentage() + "%");
                capitalization.setSelected(depositTypeEntity.getCapitalization());
                currency.setText(depositTypeEntity.getCurrency());
                term.getModel().setValue(Integer.valueOf(minTerm.getText()));
                ((SpinnerNumberModel) term.getModel()).setMinimum(Integer.valueOf(minTerm.getText()));
                summ.getModel().setValue(Double.valueOf(minSumm.getText()));
                ((SpinnerNumberModel) summ.getModel()).setMinimum(Double.valueOf(minSumm.getText()));


            }
        });
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String percentageStr = percentage.getText();
                percentageStr = percentageStr.replaceAll("[%]", "");
                Double percentageValue = Double.valueOf(percentageStr) / 100;
                Double summValue = (Double) summ.getValue();
                Integer termValue = (Integer) term.getValue();
                if (capitalization.isSelected()) {
                    resultValue = new BigDecimal(summValue * Math.pow((1 + percentageValue / 4), termValue / 3)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    profitValue = new BigDecimal(resultValue - (Double) summ.getValue()).doubleValue();
                } else {
                    resultValue = new BigDecimal(summValue + (summValue * percentageValue) * termValue / 12).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    profitValue = new BigDecimal(resultValue - summValue).doubleValue();
                }
                endSumm.setText(resultValue.toString());
                profit.setText(profitValue.toString());
            }
        });
        setPreferredSize(new Dimension(650, 400));
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

    private void initComboAndDeposit() {
        try {
            deposit = new JComboBox();
            Connection.getOutputStream().writeObject(12);
            depositTypes = (ArrayList<DepositType>) Connection.getInputStream().readObject();
            Vector<DepositType> depositsVector = new Vector<>(depositTypes);
            DefaultComboBoxModel<DepositType> comboBoxModel = new DefaultComboBoxModel<DepositType>(depositsVector);
            deposit.setModel(comboBoxModel);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void createUIComponents() {
        initComboAndDeposit();
    }

}
