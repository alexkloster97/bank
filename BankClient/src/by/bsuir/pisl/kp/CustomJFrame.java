package by.bsuir.pisl.kp;

import by.bsuir.pisl.kp.connection.Connection;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by akloster on 3/16/2018.
 */
public class CustomJFrame extends JFrame {
    public CustomJFrame (String name) {
        super(name);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Connection.close();
                System.exit(0);
            }
        });
    }

}
