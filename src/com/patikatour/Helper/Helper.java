package com.patikatour.Helper;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Helper {
    public static void setLayout() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
    }

    public static void showMessageDialog(String message) {
        String msg;
        switch (message) {
            case "fill" -> msg = "Tüm alanları doldurunuz!";
            case "done" -> msg = "İşlem başarılı.";
            case "sent" -> msg = "Mesajınız iletildi";
            case "error" -> msg = "Bir hata ile karşılaşıldı!";
            default -> msg = message;
        }
        JOptionPane.showMessageDialog(null, msg);
    }

    public static boolean showConfirmDialog(String message) {
        String msg;
        switch (message) {
            case "sure" -> msg = "Emin misiniz?";
            default -> msg = message;
        }
        return JOptionPane.showConfirmDialog(null, msg, "Seçim yapınız.", JOptionPane.YES_NO_OPTION) == 0;
    }

    public static void optionPaneToTR() {
        UIManager.put("OptionPane.okButtonText", "Tamam");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayır");
    }

    public static boolean isDateValid(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isEmpty(JTextField textField) {
        return textField.getText().trim().isEmpty();
    }

    public static boolean isEmpty(JTextArea textArea) {
        return textArea.getText().trim().isEmpty();
    }

    public static boolean isEmpty(JComboBox comboBox) {
        return comboBox.getSelectedItem().toString().trim().isEmpty();
    }
}
