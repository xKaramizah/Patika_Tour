package com.patikatour.View;

import com.patikatour.Helper.Config;
import com.patikatour.Helper.Helper;
import com.patikatour.Model.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private JComboBox<Integer> cmb_star;
    private JButton btn_hotel_add;
    private JButton btn_hotel_clear;
    private JComboBox<String> cmb_service_type;
    private JCheckBox check_carpark;
    private JCheckBox check_fitness;
    private JCheckBox check_wifi;
    private JCheckBox check_janitor;
    private JCheckBox check_spa;
    private JCheckBox check_room_service;
    private JCheckBox check_pool;
    private JFormattedTextField fld_winter_start;
    private JFormattedTextField fld_winter_end;
    private JPanel pnl_room_list;
    private JTable tbl_room_list;
    private JScrollPane scrl_room_list;
    private JComboBox<String> cmb_room_hotels;
    private JTextField fld_room_type;
    private JTextField fld_room_beds;
    private JCheckBox check_tv;
    private JCheckBox check_vault;
    private JCheckBox check_game;
    private JCheckBox check_miniBar;
    private JCheckBox check_projection;
    private JTextField fld_room_square;
    private JTextField fld_room_stock;
    private JButton btn_room_add;
    private JButton btn_room_clear;
    private JFormattedTextField fld_room_adult_price;
    private JFormattedTextField fld_room_adult_discounted;
    private JFormattedTextField fld_room_child_price;
    private JFormattedTextField fld_room_child_discounted;
    private JComboBox<String> cmb_currency_1;
    private JComboBox<String> cmb_currency_2;
    private JComboBox<String> cmb_currency_3;
    private JComboBox<String> cmb_currency_4;
    private JPanel pnl_room_add;
    private JTable tbl_booking_list;
    private JPanel pnl_booking_list;
    private DefaultTableModel mdl_hotel_list;
    private Object[] row_hotel_list;
    private JMenuItem hotelDeleteMenuItem;
    private JMenuItem roomDeleteMenuItem;
    private DefaultTableModel mdl_room_list;
    private Object[] row_room_list;
    private DefaultTableModel mdl_booking_list;
    private Object[] row_booking_list;

    public OperatorGUI(User user) {
        add(wrapper);
        setSize(1100, 500);
        setTitle(Config.PROJECT_TITLE);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        txt_welcome.setText("Yönetim Arayüzüne hoşgeldiniz Sn. " + user.getName());
        setVisible(true);

        setupHotelTable();
        setupRoomTable();
        setupBookingTable();
        setupRoomCmb();
        setupListeners(user);

    }

    private void setupBookingTable() {
        String[] colNames = {"id", "Otel Adı", "Oda Tipi", "Misafir Adı", "Telefon", "Eposta", "Not", "Giriş Tarihi", "Çıkış Tarihi"};
        mdl_booking_list = new DefaultTableModel(null, colNames);
        row_booking_list = new Object[mdl_booking_list.getColumnCount()];
        loadBookingTable();
        tbl_booking_list.setModel(mdl_booking_list);
    }

    private void loadBookingTable() {
        mdl_booking_list.setRowCount(0);
        int i;
        Room room;
        Hotel hotel;
        for (Booking book : Booking.getList()) {
            i = 0;
            row_booking_list[i++] = book.getId();
            room = Room.getFetch(book.getRoom_id());
            hotel = Hotel.getFetch("SELECT * FROM hotel WHERE id = " + room.getHotelID());
            row_booking_list[i++] = hotel.getName();
            row_booking_list[i++] = room.getType();
            row_booking_list[i++] = book.getName();
            row_booking_list[i++] = book.getPhone();
            row_booking_list[i++] = book.getEmail();
            row_booking_list[i++] = book.getNote();
            row_booking_list[i++] = book.getStart_date();
            row_booking_list[i++] = book.getEnd_date();
            mdl_booking_list.addRow(row_booking_list);
        }
    }

    private void setupRoomCmb() {
        cmb_room_hotels.addItem("");
        for (Hotel hotel : Hotel.getList()) {
            cmb_room_hotels.addItem(hotel.getName());
        }
    }

    private void setupListeners(User user) {

        btn_quit.addActionListener(e -> {
            if (Helper.showConfirmDialog("sure")) {
                dispose();
                SwingUtilities.invokeLater(() -> new SearchGUI(user));
            }
        });

        // ---- SEARCH LISTENERS ---- //

        fld_winter_start.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fld_winter_start.setText(null);
                CalendarGUI calendar = new CalendarGUI(OperatorGUI.this, fld_winter_start);
                calendar.setVisible(true);
                fld_winter_end.setEnabled(true);
            }
        });
        fld_winter_end.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Helper.isEmpty(fld_winter_start)) {
                    Helper.showMessageDialog("Başlangıç dönemini seçiniz!");
                } else {
                    fld_winter_end.setText(null);
                    CalendarGUI calendar = new CalendarGUI(OperatorGUI.this, fld_winter_end);
                    calendar.setVisible(true);
                }
            }
        });

        // END ---- SEARCH LISTENERS ---- //

        // ---- HOTEL LISTENERS ---- //

        btn_hotel_add.addActionListener(e -> {
            if (Helper.isEmpty(fld_name) || Helper.isEmpty(fld_address) || Helper.isEmpty(fld_city) || Helper.isEmpty(fld_region) || Helper.isEmpty(fld_phone) || Helper.isEmpty(fld_email) || Helper.isEmpty(cmb_star) || Helper.isEmpty(cmb_service_type) || Helper.isEmpty(fld_winter_start) || Helper.isEmpty(fld_winter_end)) {
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

                String winterStartValue = fld_winter_start.getText();
                String winterEndValue = fld_winter_end.getText();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date winterEndDate, winterStartDate;
                try {
                    winterStartDate = dateFormat.parse(winterStartValue);
                    winterEndDate = dateFormat.parse(winterEndValue);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                java.sql.Date sqlWinterStartDate = new java.sql.Date(winterStartDate.getTime());
                java.sql.Date sqlWinterEndDate = new java.sql.Date(winterEndDate.getTime());

                if (Hotel.add(name, address, city, region, phone, email, star, features, serviceType, sqlWinterStartDate, sqlWinterEndDate)) {
                    Helper.showMessageDialog("done");
                    loadHotelTable();
                    clearHotelAddFields();
                    setupRoomCmb();
                } else {
                    Helper.showMessageDialog("error");
                }
            }
        });
        btn_hotel_clear.addActionListener(e -> {
            clearHotelAddFields();
        });
        hotelDeleteMenuItem.addActionListener(e -> {
            int selectedOne = Integer.parseInt(tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 0).toString());
            if (Helper.showConfirmDialog("Otel ile ilişkili tüm diğer veriler silinecektir. Emin misiniz?")) {
                tbl_hotel_list.clearSelection();
                if (Hotel.delete(selectedOne)) {
                    Helper.showMessageDialog("done");
                } else {
                    Helper.showMessageDialog("error");
                }
                loadHotelTable();
                loadRoomTable();
            }
        });

        tbl_hotel_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                if (Helper.showConfirmDialog("Otel'in bir özelliğiniz değiştiriyorsunuz. Emin misiniz?")) {
                    int selectedID = Integer.parseInt(tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 0).toString());
                    String name = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 1).toString();
                    String address = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 2).toString();
                    String city = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 3).toString();
                    String region = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 4).toString();
                    String phone = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 5).toString();
                    String email = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 6).toString();
                    int star = Integer.parseInt(tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 7).toString());
                    String features = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 8).toString();
                    String serviceType = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 9).toString();
                    java.sql.Date winterStart = java.sql.Date.valueOf(tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 10).toString());
                    java.sql.Date winterEnd = java.sql.Date.valueOf(tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 11).toString());
                    if (Hotel.update(selectedID, name, address, city, region, phone, email, star, features, serviceType, winterStart, winterEnd)) {
                        Helper.showMessageDialog("done");
                    } else {
                        Helper.showMessageDialog("error");
                    }

                } else {
                    loadHotelTable();
                }
            }
        });

        // END ---- HOTEL LISTENERS ---- //

        // ---- ROOM LISTENERS ---- //
        btn_room_clear.addActionListener(e -> {
            clearRoomAddFields();
        });
        btn_room_add.addActionListener(e -> {
            if (Helper.isEmpty(cmb_room_hotels) || Helper.isEmpty(fld_room_beds) || Helper.isEmpty(fld_room_stock) || Helper.isEmpty(fld_room_square) || Helper.isEmpty(fld_room_type)
                    || Helper.isEmpty(fld_room_adult_price) || Helper.isEmpty(fld_room_adult_discounted) || Helper.isEmpty(fld_room_child_price) || Helper.isEmpty(fld_room_child_discounted)
                    || Helper.isEmpty(cmb_currency_1) || Helper.isEmpty(cmb_currency_2) || Helper.isEmpty(cmb_currency_3) || Helper.isEmpty(cmb_currency_4)
            ) {
                Helper.showMessageDialog("fill");
            } else {
                String type = fld_room_type.getText();
                byte bedNumber = Byte.parseByte(fld_room_beds.getText());
                boolean tv = check_tv.isSelected();
                boolean minibar = check_miniBar.isSelected();
                boolean gameConsole = check_game.isSelected();
                boolean vault = check_vault.isSelected();
                boolean projection = check_projection.isSelected();
                double squareMeter = Double.parseDouble(fld_room_square.getText());
                String hotelName = (String) cmb_room_hotels.getSelectedItem();
                int hotelID = Hotel.getFetch("SELECT * FROM hotel WHERE name = '" + hotelName + "'").getId();
                byte stock = Byte.parseByte(fld_room_stock.getText());
                double adultDiscountedPrice = Double.parseDouble(fld_room_adult_discounted.getText());
                double adultPrice = Double.parseDouble(fld_room_adult_price.getText());
                double childDiscountedPrice = Double.parseDouble(fld_room_child_discounted.getText());
                double childPrice = Double.parseDouble(fld_room_child_price.getText());
                if (Room.getFetch(hotelID, type) != null) {
                    Helper.showMessageDialog("Eklemeye çalıştığınız oda zaten mevcut.");
                } else {
                    if (Room.add(type, bedNumber, tv, minibar, gameConsole, vault, projection, squareMeter, hotelID, stock, adultDiscountedPrice, adultPrice, childDiscountedPrice, childPrice)) {
                        Helper.showMessageDialog("done");
                    } else {
                        Helper.showMessageDialog("error");
                    }
                }
                loadRoomTable();
                clearRoomAddFields();
            }
        });

        tbl_room_list.getModel().addTableModelListener(e -> {
            if ((e.getType() == TableModelEvent.UPDATE)) {
                if (Helper.showConfirmDialog("sure")) {
                    int id = Integer.parseInt(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 0).toString());
                    String type = tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 2).toString();
                    byte bedNumber = Byte.parseByte(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 3).toString());
                    boolean tv = Boolean.getBoolean(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 4).toString());
                    boolean minibar = Boolean.getBoolean(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 5).toString());
                    boolean gameConsole = Boolean.getBoolean(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 6).toString());
                    boolean vault = Boolean.getBoolean(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 7).toString());
                    boolean projection = Boolean.getBoolean(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 8).toString());
                    double squareMeter = Double.parseDouble(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 9).toString());
                    int hotelID = Hotel.getFetch("SELECT * FROM hotel WHERE name = '" + tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 1) + "'").getId();
                    byte stock = Byte.parseByte(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 10).toString());
                    double adultPrice = Double.parseDouble(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 11).toString());
                    double adultDiscountedPrice = Double.parseDouble(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 12).toString());
                    double childPrice = Double.parseDouble(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 13).toString());
                    double childDiscountedPrice = Double.parseDouble(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 14).toString());

                    if (Room.update(id, type, bedNumber, tv, minibar, gameConsole, vault, projection, squareMeter, hotelID, stock, adultDiscountedPrice, adultPrice, childDiscountedPrice, childPrice)
                    ) {
                        Helper.showMessageDialog("done");
                    } else {
                        Helper.showMessageDialog("error");
                    }
                } else {
                    loadRoomTable();
                }
            }
        });
        roomDeleteMenuItem.addActionListener(e -> {
            int selectedID = Integer.parseInt(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 0).toString());
            if (Room.delete(selectedID)) {
                Helper.showMessageDialog("done");
            } else {
                Helper.showMessageDialog("error");
            }
            loadRoomTable();
        });
        // END ---- ROOM LISTENERS ---- //
    }

    private void setupHotelTable() {
        String[] columns = {"ID", "Adı", "Adres", "Şehir", "Bölge", "Telefon", "E-Posta", "Yıldız", "Özellikler", "Servis Tipi", "İndirim Başl.", "İndirim Bitiş"};
        mdl_hotel_list = new DefaultTableModel(null, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        row_hotel_list = new Object[mdl_hotel_list.getColumnCount()];
        JPopupMenu hotelPopupMenu = new JPopupMenu();
        hotelDeleteMenuItem = new JMenuItem("Sil");
        hotelPopupMenu.add(hotelDeleteMenuItem);

        loadHotelTable();
        tbl_hotel_list.setModel(mdl_hotel_list);
        tbl_hotel_list.setComponentPopupMenu(hotelPopupMenu);
        tbl_hotel_list.getTableHeader().setReorderingAllowed(false);
        tbl_hotel_list.getColumnModel().getColumn(0).setMaxWidth(40);
        tbl_hotel_list.getColumnModel().getColumn(7).setMaxWidth(40);
        tbl_hotel_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void setupRoomTable() {
        String[] roomColumns = {"ID", "Otel Adı", "Oda Tipi", "Yatak Sayısı", "TV", "Mini Bar", "Oyun Konsolu", "Kasa", "Projeksiyon", "m2", "Toplam Adet", "Yetişkin Fiyat", "Yetişkin İnd. Fiyat", "Çocuk Fiyat", "Çocuk İnd. Fiyat"};
        mdl_room_list = new DefaultTableModel(null, roomColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        row_room_list = new Object[mdl_room_list.getColumnCount()];

        JPopupMenu roomPopupMenu = new JPopupMenu();
        roomDeleteMenuItem = new JMenuItem("Sil");
        roomPopupMenu.add(roomDeleteMenuItem);

        tbl_room_list.setModel(mdl_room_list);
        loadRoomTable();
        tbl_room_list.setComponentPopupMenu(roomPopupMenu);
        tbl_room_list.getTableHeader().setReorderingAllowed(false);
        tbl_room_list.getColumnModel().getColumn(0).setMaxWidth(40);
        tbl_room_list.getColumnModel().getColumn(3).setMaxWidth(75);
        tbl_room_list.getColumnModel().getColumn(4).setMaxWidth(50);
        tbl_room_list.getColumnModel().getColumn(5).setMaxWidth(75);
        tbl_room_list.getColumnModel().getColumn(6).setMaxWidth(75);
        tbl_room_list.getColumnModel().getColumn(7).setMaxWidth(50);
        tbl_room_list.getColumnModel().getColumn(8).setMaxWidth(75);
        tbl_room_list.getColumnModel().getColumn(9).setMaxWidth(50);
        tbl_room_list.getColumnModel().getColumn(10).setMaxWidth(75);
    }

    private void loadRoomTable() {
        mdl_room_list.setRowCount(0);
        Price priceList;
        int i;
        for (Room room : Room.getList()) {
            i = 0;
            row_room_list[i++] = room.getId();
            row_room_list[i++] = Hotel.getFetch("SELECT * FROM hotel WHERE id = " + room.getHotelID()).getName();
            row_room_list[i++] = room.getType();
            row_room_list[i++] = room.getBedNumber();
            row_room_list[i++] = room.isTv();
            row_room_list[i++] = room.isMinibar();
            row_room_list[i++] = room.isGameConsole();
            row_room_list[i++] = room.isVault();
            row_room_list[i++] = room.isProjection();
            row_room_list[i++] = room.getSquareMeter();
            row_room_list[i++] = room.getStock();
            priceList = Price.getFetch(room.getId());
            if (priceList != null) {
                row_room_list[i++] = priceList.getAdultPrice();
                row_room_list[i++] = priceList.getAdultPriceDis();
                row_room_list[i++] = priceList.getChildPrice();
                row_room_list[i++] = priceList.getChildPriceDis();
            }
            mdl_room_list.addRow(row_room_list);
        }
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
            row_hotel_list[i++] = hotel.getWinterStart();
            row_hotel_list[i++] = hotel.getWinterEnd();
            mdl_hotel_list.addRow(row_hotel_list);
        }
    }

    private void clearRoomAddFields() {
        fld_room_beds.setText(null);
        fld_room_stock.setText(null);
        fld_room_square.setText(null);
        fld_room_type.setText(null);
        cmb_room_hotels.setSelectedItem(null);
        check_game.setSelected(false);
        check_miniBar.setSelected(false);
        check_tv.setSelected(false);
        check_vault.setSelected(false);
        check_projection.setSelected(false);
        fld_room_adult_price.setText(null);
        fld_room_adult_discounted.setText(null);
        fld_room_child_price.setText(null);
        fld_room_child_discounted.setText(null);
        cmb_currency_1.setSelectedItem("TL");
        cmb_currency_2.setSelectedItem("TL");
        cmb_currency_3.setSelectedItem("TL");
        cmb_currency_4.setSelectedItem("TL");
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
        fld_winter_start.setText(null);
        fld_winter_end.setText(null);
    }
}
