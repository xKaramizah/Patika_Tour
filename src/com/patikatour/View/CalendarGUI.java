package com.patikatour.View;

import com.patikatour.Helper.Config;

import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class CalendarGUI extends JFrame {

    private DefaultTableModel model;
    private Calendar cal = new GregorianCalendar();
    private JLabel label;
    private JTextField textField;
    private SimpleDateFormat dateFormat;

    public CalendarGUI(JFrame parent,JTextField textField) {
        this.textField = textField;
        this.dateFormat =new SimpleDateFormat("dd/MM/yyyy");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.CALENDAR_TITLE);
        setSize(300, 200);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);
        label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JButton b1 = new JButton("<<");
        b1.addActionListener(e -> {
            cal.add(Calendar.MONTH, -1);
            updateMonth();
        });

        JButton b2 = new JButton(">>");
        b2.addActionListener(e -> {
            cal.add(Calendar.MONTH, +1);
            updateMonth();
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(b1, BorderLayout.WEST);
        panel.add(label, BorderLayout.CENTER);
        panel.add(b2, BorderLayout.EAST);

        String[] columns = {"Pzr", "Pzts", "Salı", "Çrş", "Prş", "Cuma", "Cmts"};
        model = new DefaultTableModel(null, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model) {
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                    Object value = table.getValueAt(row, col);
                    if (value != null) {
                        int day = (int) value;
                        cal.set(Calendar.DAY_OF_MONTH, day);
                        String selectedDate = dateFormat.format(cal.getTime());
                        textField.setText(selectedDate);
                        dispose();
                    }
                }
            }
        });
        JScrollPane pane = new JScrollPane(table);

        this.add(panel, BorderLayout.NORTH);
        this.add(pane, BorderLayout.CENTER);

        this.updateMonth();

    }

    private void updateMonth() {
        cal.set(Calendar.DAY_OF_MONTH, 1);

        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        int year = cal.get(Calendar.YEAR);
        label.setText(month + " " + year);

        int startDay = cal.get(Calendar.DAY_OF_WEEK);
        int numberOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
       // int weeks = cal.get(Calendar.WEEK_OF_MONTH);

        model.setRowCount(0);
        model.setRowCount(6);

        int i = startDay - 1;
        for (int day = 1; day <= numberOfDays; day++) {
            model.setValueAt(day, i / 7, i % 7);
            i = i + 1;
        }
    }
}