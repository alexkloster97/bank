package by.bsuir.pisl.kp;

import by.bsuir.pisl.kp.connection.Connection;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import sun.reflect.generics.tree.ClassTypeSignature;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

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

    public void reloadTable(JTable table) {

    }

    protected TableModel createTableModel(int rowCount, Map<Integer, Column> colums) {
        TableModel tableModel = new DefaultTableModel(rowCount, colums.size()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return colums.get(column).getEditable();
            }

            @Override
            public String getColumnName(int index) {
                return colums.get(index).getName();
            }


            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return colums.get(columnIndex).getType();
            }

        };
        return tableModel;
    }



    protected void setColumnWidth(JTable table, Map<Integer, Column> colums) {
        for(int i = 0; i<colums.size(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(colums.get(i).getWidth());
        }
    }

    protected class Column {
        private Class type;
        private String name;
        private Boolean editable;

        public Integer getWidth() {
            return width;
        }

        private Integer width;

        public Column(Class type, String name, Boolean editable, Integer width) {
            this.type = type;
            this.name = name;
            this.editable = editable;
            this.width = width;
        }

        public Column(Class type, String name, Boolean editable) {
            this.type = type;
            this.name = name;
            this.editable = editable;
        }

        public Boolean getEditable() {
            return editable;
        }

        public Class getType() {
            return type;
        }

        public void setType(Class type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
