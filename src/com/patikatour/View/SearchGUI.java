package com.patikatour.View;

import com.patikatour.Helper.Config;
import com.patikatour.Model.MenuBar;
import com.patikatour.Model.User;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_mid;
    private JPanel pnl_bottom;
    private JTextField fld_area;
    private JTextField fld_enter_date;
    private JTextField fld_exit_date;
    private JComboBox cmb_adult;
    private JComboBox cmb_child;
    private JButton btn_search;

    public SearchGUI(User user) {
        add(wrapper);
        setTitle(Config.PROJECT_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Config.SCREEN_WIDTH, 325);
        setLocationRelativeTo(null);
        setResizable(false);
        fld_exit_date.setEnabled(false);

        new MenuBar(this, user);

        setupListeners();

        setVisible(true);
    }

    private void setupListeners() {

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
    }

    private void enableExitDateField() {
        fld_exit_date.setEnabled(!fld_enter_date.getText().trim().isEmpty());
    }

}