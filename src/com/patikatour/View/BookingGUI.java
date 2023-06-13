package com.patikatour.View;

import com.patikatour.Helper.Config;
import com.patikatour.Helper.Helper;
import com.patikatour.Model.Booking;
import com.patikatour.Model.Hotel;
import com.patikatour.Model.Room;
import com.patikatour.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class BookingGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_contact_name;
    private JTextField fld_contact_tel;
    private JTextField fld_contact_mail;
    private JTextArea fld_note;
    private JLabel txt_hotel_name;
    private JLabel txt_room_photo;
    private JLabel txt_city;
    private JLabel txt_address;
    private JLabel txt_region;
    private JLabel txt_features;
    private JLabel txt_type;
    private JLabel txt_enterDate;
    private JLabel txt_exitDate;
    private JLabel txt_adultNo;
    private JLabel txt_childNo;
    private JLabel txt_details;
    private JLabel txt_nights;
    private JLabel txt_price;
    private JLabel txt_tel;
    private JPanel pnl_top;
    private JPanel pnl_hotel;
    private JPanel pnl_room;
    private JPanel pnl_book;
    private JButton btn_book;
    private JLabel txt_service;
    private JPanel pnl_guest;
    private JPanel pnl_star;


    public BookingGUI(User user, Hotel hotel, Room room, int adultNo, int childNo, java.sql.Date enterDateSql, java.sql.Date exitDateSql, double price) {
        add(wrapper);
        setTitle(Config.PROJECT_TITLE);
        setSize(650, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        setVisible(true);


        // ---- SET TEXTS & FIELDS ---- //
        txt_hotel_name.setText(Hotel.getFetch("SELECT * FROM hotel WHERE id = " + room.getHotelID()).getName());
        txt_city.setText(hotel.getCity());
        txt_region.setText(hotel.getRegion());
        txt_address.setText(hotel.getAddress());
        txt_features.setText(hotel.getFeatures());
        txt_tel.setText(hotel.getPhone());
        txt_type.setText(room.getType());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String enterDate, exitDate;

        enterDate = dateFormat.format(enterDateSql);
        exitDate = dateFormat.format(exitDateSql);

        txt_enterDate.setText("» Giriş tarihi: " + enterDate);
        txt_exitDate.setText("» Çıkış tarihi: " + exitDate);
        txt_adultNo.setText("» " + adultNo + " yetişkin");
        if (childNo > 0) {
            txt_childNo.setText("» " + childNo + " çocuk");
        } else {
            txt_childNo.setVisible(false);
        }

        String roomDetails = "";
        if (room.isTv()) {
            roomDetails = roomDetails + "TV: Var ";
        }
        if (room.isMinibar()) {
            roomDetails = roomDetails + "Minibar: Var ";
        }
        if (room.isVault()) {
            roomDetails = roomDetails + "Kasa: Var ";
        }
        if (room.isGameConsole()) {
            roomDetails = roomDetails + "Oyun Konsolu: Var ";
        }
        if (room.isProjection()) {
            roomDetails = roomDetails + "Projeksiyon: Var ";
        }

        txt_details.setText(roomDetails);
        long nightsMillis = exitDateSql.getTime() - enterDateSql.getTime();
        int nights = (int) TimeUnit.MILLISECONDS.toDays(nightsMillis);
        txt_nights.setText(nights + " Gece için");
        txt_price.setText(price + " TL");
        txt_service.setText(hotel.getServiceType() + " :");
        if (!user.getType().equals("visitor")) {
            fld_contact_name.setText(user.getName());
            fld_contact_mail.setText(user.getEmail());
        }
        // END ---- SET TEXTS && FIELDS---- //

        // ---- SET STARS ---- //
        pnl_star.setLayout(new GridLayout(1, 5));
        for (int i = 0; i < hotel.getStar(); i++) {
            ImageIcon starIcon = new ImageIcon("images/star.png");
            Image scaledImage = starIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            JLabel starLabel = new JLabel(new ImageIcon(scaledImage));
            pnl_star.add(starLabel);
        }
        // END ---- SET STARS ---- //

        // ---- BOOKING TABLE ---- //

        pnl_guest.setLayout(new GridLayout(adultNo + childNo, 4));
        pnl_guest.setBackground(new Color(250, 250, 250));
        //  pnl_guest.setBorder(new EmptyBorder(0,10,10,10));

        JTextField[] textFields = new JTextField[(adultNo + childNo) * 4];
        JTextField textField;
        JLabel lbl;
        for (int i = 0; i < (adultNo + childNo); i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 0) {
                    lbl = new JLabel();
                    lbl.setText((i + 1) + ". Misafir");
                    lbl.setHorizontalAlignment(SwingConstants.CENTER);
                    pnl_guest.add(lbl);
                } else {

                    textField = new JTextField();
                    textFields[i * 4 + j] = textField;
                    textField.setForeground(Color.LIGHT_GRAY);
                    textField.setPreferredSize(new Dimension(150, 25));
                    switch (j % 4) {
                        case 1 -> textField.setText("Ad Soyad");
                        case 2 -> textField.setText("Uyruğu");
                        case 3 -> textField.setText("Kimlik/Pasaport No");
                    }
                    pnl_guest.add(textField);
                }
            }
        }

        // END ---- BOOKING TABLE ---- //

        pack();

        // ---- LISTENERS ---- //
        for (int i = 0; i < textFields.length; i++) {
            JTextField clearField = textFields[i];
            if (clearField != null) {
                clearField.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        clearField.setText("");
                        clearField.setBackground(Color.WHITE);
                        clearField.setForeground(Color.BLACK);
                    }
                });
            }
        }

        btn_book.addActionListener(e -> {
            boolean isEmpty = false;
            for (int i = 0; i < textFields.length; i++) {
                if (i % 4 != 0) {
                    JTextField checkField = textFields[i];
                    isEmpty = checkField == null;
                    break;
                }
            }
            if (Helper.isEmpty(fld_contact_name) || Helper.isEmpty(fld_contact_mail) || Helper.isEmpty(fld_contact_tel) || isEmpty) {
                Helper.showMessageDialog("fill");
            } else {
                String name = fld_contact_name.getText();
                String phone = fld_contact_tel.getText();
                String email = fld_contact_mail.getText();
                String note = fld_note.getText();
                if (Booking.add(enterDateSql,exitDateSql,name,phone,email,note,room)){
                    Helper.showMessageDialog("Rezervasyonunuz oluşturuldu.");
                } else {
                    Helper.showMessageDialog("Seçtiğiniz tarihlerde bu oda tipi müsait değil");
                }
            }
        });
        // END ---- LISTENERS ---- //

    }

    private void createUIComponents() {
        txt_room_photo = new JLabel();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("images/gorselyok.jpg", "Oda görseli").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        txt_room_photo.setIcon(imageIcon);
    }
}
