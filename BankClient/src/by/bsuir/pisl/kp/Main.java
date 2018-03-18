package by.bsuir.pisl.kp;

import by.bsuir.pisl.kp.authorization.Authorization;

import javax.swing.*;

/**
 * Created by akloster on 3/5/2018.
 */
public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
        }
        new Authorization();
    }
}
