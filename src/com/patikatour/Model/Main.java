package com.patikatour.Model;

import com.patikatour.Helper.Helper;
import com.patikatour.View.SearchGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Helper.setLayout();
        Helper.optionPaneToTR();
        User user = new Visitor(1, "visitor", "visitor", "1", "1", "operator");
        SwingUtilities.invokeLater(() -> new SearchGUI(user));
    }
}
