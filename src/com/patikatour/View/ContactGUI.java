package com.patikatour.View;

import com.patikatour.Helper.Config;
import com.patikatour.Helper.Helper;
import com.patikatour.Model.MenuBar;
import com.patikatour.Model.User;

import javax.swing.*;

public class ContactGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_bottom;
    private JPanel pnl_mid;
    private JTextField fld_name;
    private JTextField fld_email;
    private JTextArea fld_txt;
    private JButton btn_send_msg;
    private JLabel txt_comp_email;
    private JLabel txt_comp_phone;
    private JLabel txt_comp_address;
    private JLabel txt_bottom_1;
    private JLabel txt_bottom_2;

    public ContactGUI(User user) {
        setTitle(Config.PROJECT_TITLE);
        pack();
        setSize(Config.SCREEN_WIDTH, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        txt_comp_address.setText(Config.PROJECT_ADDRESS);
        txt_comp_email.setText(Config.PROJECT_MAIL);
        txt_comp_phone.setText(Config.PROJECT_PHONE);
        txt_bottom_1.setText(Config.PROJECT_BOTTOM_MSG_1);
        txt_bottom_2.setText(Config.PROJECT_BOTTOM_MSG_2);
        new MenuBar(this, user);

        add(wrapper);
        btn_send_msg.addActionListener(e -> {
            if (Helper.isEmpty(fld_name) || Helper.isEmpty(fld_txt) || Helper.isEmpty(fld_email)) {
                Helper.showMessageDialog("fill");
            } else {
                Helper.showMessageDialog("sent");
            }
        });
    }
}
