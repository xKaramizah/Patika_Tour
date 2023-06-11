package com.patikatour.View;

import com.patikatour.Helper.Config;
import com.patikatour.Helper.Helper;
import com.patikatour.Model.MenuBar;
import com.patikatour.Model.Room;
import com.patikatour.Model.User;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SearchGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_mid;
    private JPanel pnl_bottom;
    private JTextField fld_area;
    private JTextField fld_enter_date;
    private JTextField fld_exit_date;
    private JComboBox<String> cmb_adult;
    private JComboBox<String> cmb_child;
    private JButton btn_search;

    public SearchGUI(User user) {
        add(wrapper);
        setTitle(Config.PROJECT_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Config.SCREEN_WIDTH, 325);
        setLocationRelativeTo(null);
        setResizable(false);
        fld_exit_date.setEnabled(false);
        setTodayDate(fld_enter_date);
        setTomorrowDate(fld_exit_date);

        new MenuBar(this, user);

        setupListeners(user);

        setVisible(true);
    }

    private void setupListeners(User user) {

        fld_enter_date.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                CalendarGUI calendar = new CalendarGUI(SearchGUI.this, fld_enter_date);
                calendar.setVisible(true);
            }
        });

        fld_enter_date.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                enableExitDateField();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                enableExitDateField();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enableExitDateField();
            }

        });

        fld_exit_date.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (fld_exit_date.isEnabled()) {
                    CalendarGUI calendar = new CalendarGUI(SearchGUI.this, fld_exit_date);
                    calendar.setVisible(true);
                }
            }
        });

        btn_search.addActionListener(e -> {
            if (Helper.isEmpty(fld_area) || Helper.isEmpty(fld_enter_date) || Helper.isEmpty(fld_exit_date) || Helper.isEmpty(cmb_adult) || Helper.isEmpty(cmb_child)) {
                Helper.showMessageDialog("Uygun otel için arama kriterlerini giriniz.");
            } else {
                String area = fld_area.getText();
                int adult = Byte.parseByte(cmb_adult.getSelectedItem().toString());
                int child = Byte.parseByte(cmb_child.getSelectedItem().toString());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
                Date enterDate, exitDate;
                try {
                    enterDate = dateFormat.parse(fld_enter_date.getText());
                    exitDate = dateFormat.parse(fld_exit_date.getText());
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                java.sql.Date enterDateSql = new java.sql.Date(enterDate.getTime());
                java.sql.Date exitDateSql = new java.sql.Date(exitDate.getTime());

                ArrayList<Room> searchRooms = Room.searchList(area, (adult + child), enterDateSql, exitDateSql);

                dispose();
                SwingUtilities.invokeLater(() -> new ResultGUI(user, searchRooms, adult, child, enterDateSql, exitDateSql));
            }
        });
    }

    private void enableExitDateField() {
        fld_exit_date.setEnabled(!fld_enter_date.getText().trim().isEmpty());
    }

    public void setTodayDate(JTextField textField) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Calendar calendar = Calendar.getInstance();
        String todayDate = dateFormat.format(calendar.getTime());

        textField.setText(todayDate);
    }

    public void setTomorrowDate(JTextField textField) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        String tomorrowDate = dateFormat.format(calendar.getTime());

        textField.setText(tomorrowDate);
    }

}