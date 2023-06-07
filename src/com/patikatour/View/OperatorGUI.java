package com.patikatour.View;

import com.patikatour.Helper.Config;
import com.patikatour.Helper.Helper;
import com.patikatour.Model.Hotel;
import com.patikatour.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JButton btn_quit;
    private JTabbedPane tab_operator;
    private JPanel pnl_hotel_list;
    private JLabel txt_welcome;
    private JPanel pnl_hotel_add;
    private JScrollPane scrl_hotel_list;
    private JTable tbl_hotel_list;
    private JTextField fld_name;
    private JTextField fld_address;
    private JTextField fld_city;
    private JTextField fld_region;
    private JTextField fld_phone;
    private JTextField fld_email;
    private JComboBox cmb_star;
    private JButton btn_add;
    private JButton btn_clear;
    private JComboBox cmb_service_type;
    private JCheckBox check_carpark;
    private JCheckBox check_fitness;
    private JCheckBox check_wifi;
    private JCheckBox check_janitor;
    private JCheckBox check_spa;
    private JCheckBox check_room_service;
    private JCheckBox check_pool;
    private JFormattedTextField fld_period_1;
    private JFormattedTextField fld_period_2;
    private JFormattedTextField fld_period_3;
    private JFormattedTextField fld_period_4;
    private DefaultTableModel mdl_hotel_list;
    private Object[] row_hotel_list;
    private JPopupMenu popupMenu;
    private JMenuItem deleteMenu;

    public OperatorGUI(User user) {
        add(wrapper);
        setSize(800, 450);
        setTitle(Config.PROJECT_TITLE);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        txt_welcome.setText("Yönetim Ekranı'na hoşgeldiniz Sn. " + user.getName());
        setVisible(true);

        setupHotelTable();
        setupListeners(user);
    }

    private void setupListeners(User user) {
        btn_quit.addActionListener(e -> {
            if (Helper.showConfirmDialog("sure")) {
                dispose();
                SwingUtilities.invokeLater(() -> new SearchGUI(user));
            }
        });

        btn_add.addActionListener(e -> {
            if (Helper.isEmpty(fld_name) || Helper.isEmpty(fld_address) || Helper.isEmpty(fld_city) || Helper.isEmpty(fld_region) || Helper.isEmpty(fld_phone) ||
                    Helper.isEmpty(fld_email) || Helper.isEmpty(cmb_star) || Helper.isEmpty(cmb_service_type) || Helper.isEmpty(fld_period_1) || Helper.isEmpty(fld_period_2) ||
                    Helper.isEmpty(fld_period_3) || Helper.isEmpty(fld_period_4)) {
                Helper.showMessageDialog("fill");
            } else {
                String name = fld_name.getText().trim();
                String address = fld_address.getText().trim();
                String city = fld_city.getText().trim();
                String region = fld_region.getText().trim();
                String phone = fld_phone.getText().trim();
                String email = fld_email.getText().trim();
                int star = Integer.parseInt(cmb_star.getSelectedItem().toString());
                String features = "";
                if (check_carpark.isSelected()) {
                    features = features + check_carpark.getText() + " ";
                }
                if (check_wifi.isSelected()) {
                    features = features + check_wifi.getText() + " ";
                }
                if (check_pool.isSelected()) {
                    features = features + check_pool.getText() + " ";
                }
                if (check_fitness.isSelected()) {
                    features = features + check_fitness.getText() + " ";
                }
                if (check_janitor.isSelected()) {
                    features = features + check_janitor.getText() + " ";
                }
                if (check_spa.isSelected()) {
                    features = features + check_spa.getText() + " ";
                }
                if (check_room_service.isSelected()) {
                    features = features + check_room_service.getText() + " ";
                }
                String serviceType = cmb_service_type.getSelectedItem().toString();
                Date first_period_start = (Date) fld_period_1.getValue();
                Date first_period_end = (Date) fld_period_2.getValue();
                Date second_period_start = (Date) fld_period_3.getValue();
                Date second_period_end = (Date) fld_period_4.getValue();
                if (Hotel.add(name, address, city, region, phone, email, star, features, serviceType)) {
                    Helper.showMessageDialog("done");
                    loadHotelTable();
                    clearHotelAddFields();
                } else {
                    Helper.showMessageDialog("error");
                }
            }
        });
        btn_clear.addActionListener(e -> {
            clearHotelAddFields();
        });
        deleteMenu.addActionListener(e -> {
            int selectedOne = Integer.parseInt(tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 0).toString());
            if (Helper.showConfirmDialog("sure")) {
                tbl_hotel_list.clearSelection();
                Hotel.delete(selectedOne);
                Helper.showMessageDialog("done");
                loadHotelTable();
            }
        });

        fld_period_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fld_period_1.setText(null);
                CalendarGUI calendar = new CalendarGUI(OperatorGUI.this, fld_period_1);
                calendar.setVisible(true);
                fld_period_2.setEnabled(true);
            }
        });
        fld_period_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Helper.isEmpty(fld_period_1)) {
                    Helper.showMessageDialog("Başlangıç dönemini seçiniz!");
                } else {
                    fld_period_2.setText(null);
                    CalendarGUI calendar = new CalendarGUI(OperatorGUI.this, fld_period_2);
                    calendar.setVisible(true);
                }
            }
        });
        fld_period_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fld_period_3.setText(null);
                CalendarGUI calendar = new CalendarGUI(OperatorGUI.this, fld_period_3);
                calendar.setVisible(true);
                fld_period_4.setEnabled(true);
            }
        });
        fld_period_4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Helper.isEmpty(fld_period_3)) {
                    Helper.showMessageDialog("Başlangıç dönemini seçiniz!");
                } else {
                    fld_period_4.setText(null);
                    CalendarGUI calendar = new CalendarGUI(OperatorGUI.this, fld_period_4);
                    calendar.setVisible(true);
                }
            }
        });
    }

    private void clearHotelAddFields() {
        fld_name.setText(null);
        fld_address.setText(null);
        fld_city.setText(null);
        fld_region.setText(null);
        fld_email.setText(null);
        fld_phone.setText(null);
        check_room_service.setSelected(false);
        check_spa.setSelected(false);
        check_janitor.setSelected(false);
        check_fitness.setSelected(false);
        check_pool.setSelected(false);
        check_wifi.setSelected(false);
        check_carpark.setSelected(false);
        cmb_service_type.setSelectedItem(null);
        cmb_star.setSelectedItem(null);
        fld_period_1.setText(null);
        fld_period_2.setText(null);
        fld_period_3.setText(null);
        fld_period_4.setText(null);
    }

    private void setupHotelTable() {
        String[] columns = {"ID", "Adı", "Adres", "Şehir", "Bölge", "Telefon", "E-Posta", "Yıldız", "Özellikler", "Servis Tipi"};
        mdl_hotel_list = new DefaultTableModel(null, columns);
        row_hotel_list = new Object[mdl_hotel_list.getColumnCount()];
        popupMenu = new JPopupMenu();
        deleteMenu = new JMenuItem("Sil");
        popupMenu.add(deleteMenu);

        loadHotelTable();
        tbl_hotel_list.setModel(mdl_hotel_list);
        tbl_hotel_list.setComponentPopupMenu(popupMenu);
        tbl_hotel_list.getTableHeader().setReorderingAllowed(false);
        tbl_hotel_list.getColumnModel().getColumn(0).setMaxWidth(40);
        tbl_hotel_list.getColumnModel().getColumn(7).setMaxWidth(40);
        tbl_hotel_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void loadHotelTable() {
        mdl_hotel_list.setRowCount(0);
        int i;
        for (Hotel hotel : Hotel.getList()) {
            i = 0;
            row_hotel_list[i++] = hotel.getId();
            row_hotel_list[i++] = hotel.getName();
            row_hotel_list[i++] = hotel.getAddress();
            row_hotel_list[i++] = hotel.getCity();
            row_hotel_list[i++] = hotel.getRegion();
            row_hotel_list[i++] = hotel.getPhone();
            row_hotel_list[i++] = hotel.getEmail();
            row_hotel_list[i++] = hotel.getStar();
            row_hotel_list[i++] = hotel.getFeatures();
            row_hotel_list[i++] = hotel.getServiceType();
            mdl_hotel_list.addRow(row_hotel_list);
        }
    }
}
