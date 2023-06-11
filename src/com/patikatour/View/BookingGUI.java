package com.patikatour.View;

import com.patikatour.Helper.Config;
import com.patikatour.Model.User;

import javax.swing.*;

public class BookingGUI extends JFrame {
    private JPanel wrapper;
    private JTextField textField1;
    private JTextField textField3;
    private JTextField textField4;
    private JTextArea textArea1;

    public BookingGUI(User user) {
        add(wrapper);
        setTitle(Config.PROJECT_TITLE);
        setSize(Config.SCREEN_WIDTH, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setAlwaysOnTop(true);


        setVisible(true);
    }
}
