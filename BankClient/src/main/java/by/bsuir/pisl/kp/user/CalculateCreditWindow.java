package by.bsuir.pisl.kp.user;

import by.bsuir.pisl.kp.CustomJFrame;
import by.bsuir.pisl.kp.connection.Connection;
import credit.Credit;
import credit.CreditType;
import deposit.DepositType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Vector;

public class CalculateCreditWindow extends JFrame{
    private JPanel panel;
    private JComboBox creditType;
    private JTextField percentage;
    private JTextField minTerm;
    private JTextField minSumm;
    private JSpinner term;
    private JSpinner summ;
    private JTextField monthPay;
    private JTextField overPay;
    private JButton calculateButton;
    private JTextField currency;

    private Double monthPaySumm;
    private Double overPaySumm;

    private ArrayList <CreditType> creditTypes = new ArrayList<>();

    CalculateCreditWindow() {
        super("Расчет");
        initSpinners();
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
                String percentageStr = percentage.getText();
                percentageStr = percentageStr.replaceAll("[%]", "");
                Double percentageValue = Double.valueOf(percentageStr) / 100;
                Double summValue = (Double) summ.getValue();
                Integer termValue = (Integer) term.getValue();
                monthPaySumm = new BigDecimal((summValue + (summValue * percentageValue * termValue / 12))/termValue).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();;
                overPaySumm = new BigDecimal((summValue + (summValue * percentageValue * termValue / 12)) - summValue).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                monthPay.setText(monthPaySumm.toString());
                overPay.setText(overPaySumm.toString());
            }
        });
        setPreferredSize(new Dimension(650, 400));
        pack();
        setContentPane(panel);
        setVisible(true);
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


    private void createUIComponents() {
        initComboCredit();
    }
}
