package com.patikatour.View;

import com.patikatour.Helper.Config;
import com.patikatour.Model.*;
import com.patikatour.Model.MenuBar;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ResultGUI extends JFrame {
    private JPanel wrapper;
    private JTable tbl_result_list;
    private JLabel txt_bottom_1;
    private JLabel txt_bottom_2;
    private JScrollPane scrl_result_list;
    private DefaultTableModel mdl_result_list;
    private Object[] row_result_list;

    public ResultGUI(User user, ArrayList<Room> roomList, int adult, int child, java.sql.Date enterDateSql, java.sql.Date exitDateSql) {
        add(wrapper);
        setSize(Config.SCREEN_WIDTH, 500);
        setTitle(Config.PROJECT_TITLE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        txt_bottom_1.setText(Config.PROJECT_BOTTOM_MSG_1);
        txt_bottom_2.setText(Config.PROJECT_BOTTOM_MSG_2);
        new MenuBar(this, user);

        tbl_result_list.setShowHorizontalLines(true);
        tbl_result_list.setShowVerticalLines(false);
        tbl_result_list.setGridColor(Color.BLUE);
        tbl_result_list.setRowHeight(40);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tbl_result_list.setDefaultRenderer(Object.class, centerRenderer);

        setupResultTable(roomList, adult, child, enterDateSql, exitDateSql);

        setVisible(true);
        setupListeners(user);
    }

    private void setupListeners(User user) {
        tbl_result_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    new BookingGUI(user);
                }
            }
        });
    }

    private void setupResultTable(ArrayList<Room> roomList, int adult, int child, java.sql.Date enterDateSql, java.sql.Date exitDateSql) {
        String[] columns = {"Şehir", "Otel Adı", "Oda Tipi", "Fiyatı"};
        mdl_result_list = new DefaultTableModel(null, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbl_result_list.setModel(mdl_result_list);
        row_result_list = new Object[mdl_result_list.getColumnCount()];
        loadResultTable(roomList, adult, child, enterDateSql, exitDateSql);
    }

    private void loadResultTable(ArrayList<Room> roomList, int adult, int child, java.sql.Date enterDateSql, java.sql.Date exitDateSql) {
        mdl_result_list.setRowCount(0);
        Price price;
        Hotel hotel;
        Period period;
        double calculatedPrice;
        long dayCountMillis = exitDateSql.getTime() - enterDateSql.getTime();
        int dayCount = (int) TimeUnit.MILLISECONDS.toDays(dayCountMillis);
        int i;
        for (Room room : roomList) {
            i = 0;
            hotel = Hotel.getFetch("SELECT * FROM hotel WHERE id = " + room.getHotelID());
            period = Period.getFetch(hotel.getId());
            price = Price.getFetch(room.getId());
            if (enterDateSql.after(period.getStartDate()) && enterDateSql.before(period.getEndDate())) {
                calculatedPrice = dayCount * ((adult * price.getAdultPriceDis()) + (child * price.getChildPriceDis()));
            } else {
                calculatedPrice = dayCount * ((adult * price.getAdultPrice()) + (child * price.getChildPrice()));
            }

            row_result_list[i++] = hotel.getCity();
            row_result_list[i++] = hotel.getName();
            row_result_list[i++] = room.getType();
            row_result_list[i++] = calculatedPrice;
            mdl_result_list.addRow(row_result_list);
        }
    }
}
