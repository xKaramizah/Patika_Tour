package com.patikatour.View;

import com.patikatour.Helper.Config;
import com.patikatour.Helper.Helper;
import com.patikatour.Model.User;

import javax.swing.*;

public class LoginGUI extends JFrame {
    private JPanel wrapper;
    private JButton btn_register;
    private JButton btn_login;
    private JButton btn_back;
    private JPasswordField fld_pass;
    private JTextField fld_username;
    private JPanel pnl_login;
    private JPanel pnl_top;
    private JPanel pnl_bottom;
    private JPanel pnl_logo;

    public LoginGUI(User user) {
        add(wrapper);
        setTitle(Config.PROJECT_TITLE);
        setSize(275, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        loadListeners(user);

    }

    private void loadListeners(User user) {
        btn_back.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new SearchGUI(user));
        });
        btn_login.addActionListener(e -> {
            if (Helper.isEmpty(fld_username) || Helper.isEmpty(fld_pass)) {
                Helper.showMessageDialog("fill");
            } else {
                User newUser = User.getFetch(fld_username.getText().trim(), fld_pass.getText());
                if (newUser != null) {
                    dispose();
                    SwingUtilities.invokeLater(() -> new SearchGUI(newUser));
                } else {
                    Helper.showMessageDialog("Hatalı giriş!");
                }
            }
        });
        btn_register.addActionListener(e -> {
            Helper.showMessageDialog("Yakında!");
        });
    }
}
