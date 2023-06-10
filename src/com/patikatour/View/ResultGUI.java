package com.patikatour.View;

import com.patikatour.Helper.Config;
import com.patikatour.Model.Hotel;
import com.patikatour.Model.MenuBar;
import com.patikatour.Model.Room;
import com.patikatour.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ResultGUI extends JFrame {
    private JPanel wrapper;
    private JTable tbl_result_list;
    private JLabel txt_bottom_1;
    private JLabel txt_bottom_2;
    private JScrollPane scrl_result_list;
    private DefaultTableModel mdl_result_list;
    private Object[] row_result_list;

    public ResultGUI(User user, ArrayList<Room> roomList) {
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

        setupResultTable(roomList);

        setVisible(true);
    }

    private void setupResultTable(ArrayList<Room> roomList) {
        String[] columns = {"Şehir", "Otel Adı", "Oda Tipi", "Fiyatı"};
        mdl_result_list = new DefaultTableModel(null, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbl_result_list.setModel(mdl_result_list);
        row_result_list = new Object[mdl_result_list.getColumnCount()];
        loadResultTable(roomList);
    }

    private void loadResultTable(ArrayList<Room> roomList) {
        mdl_result_list.setRowCount(0);
        int i;
        for (Room room : roomList) {
            i = 0;
            Hotel hotel = Hotel.getFetch("SELECT * FROM hotel WHERE id = " + room.getHotelID());
            row_result_list[i++] = hotel.getCity();
            row_result_list[i++] = hotel.getName();
            row_result_list[i++] = room.getType();
            mdl_result_list.addRow(row_result_list);
        }
    }
}
