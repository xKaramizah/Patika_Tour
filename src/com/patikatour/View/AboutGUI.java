package com.patikatour.View;

import com.patikatour.Helper.Config;
import com.patikatour.Model.MenuBar;
import com.patikatour.Model.User;

import javax.swing.*;

public class AboutGUI extends JFrame {
    private JPanel wrapper;
    private JLabel txt_bottom_1;
    private JLabel txt_bottom_2;

    public AboutGUI(User user) {
        add(wrapper);
        setSize(Config.SCREEN_WIDTH, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle(Config.PROJECT_TITLE);
        new MenuBar(this, user);

        txt_bottom_1.setText(Config.PROJECT_BOTTOM_MSG_1);
        txt_bottom_2.setText(Config.PROJECT_BOTTOM_MSG_2);
    }
}
