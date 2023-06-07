package com.patikatour.Model;

import com.patikatour.Helper.Helper;
import com.patikatour.View.*;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MenuBar implements ActionListener, MenuListener {
    private final JMenuBar menuBar;
    private final JMenu menu_search;
    private final JMenu menu_user;
    private final JMenu menu_aboutUs;
    private final JMenu menu_contact;
    private final JMenu menu_exit;
    private final JMenuItem menu_item_login;
    private final JMenuItem menu_item_settings;
    private final JMenuItem menu_item_logout;
    private final JMenuItem menu_item_admin;
    private JFrame parent;
    private User user;

    public MenuBar(JFrame parent, User user) {
        this.parent = parent;
        this.user = user;
        this.menuBar = new JMenuBar();
        this.menu_search = new JMenu("Arama Yap");
        this.menu_user = new JMenu("Üyelik");
        this.menu_aboutUs = new JMenu("Hakkımızda");
        this.menu_contact = new JMenu("İletişim");
        this.menu_exit = new JMenu("Programı Sonlandır");

        this.menu_item_login = new JMenuItem("Üye Girişi");
        this.menu_item_settings = new JMenuItem("Ayarlar");
        this.menu_item_admin = new JMenuItem("Yönetici Paneli");
        this.menu_item_logout = new JMenuItem("Çıkış Yap");

        menu_search.setMnemonic(KeyEvent.VK_A); // Alt + A -> Anasayfa

        if (this.user.getType().equals("visitor")) {
            menu_user.add(menu_item_login);
        }
        if (!this.user.getType().equals("visitor")) {
            menu_user.add(menu_item_settings);
        }
        if (this.user.getType().equals("operator")) {
            menu_user.add(menu_item_admin);
        }
        if (!this.user.getType().equals("visitor")) {
            menu_user.add(menu_item_logout);
        }

        menuBar.add(menu_search);
        menuBar.add(menu_user);
        menuBar.add(menu_aboutUs);
        menuBar.add(menu_contact);
        menuBar.add(menu_exit);
        setupMenuListeners();
        this.parent.setJMenuBar(getMenuBar());
    }

    private void setupMenuListeners() {
        menu_search.addMenuListener(this);
        menu_aboutUs.addMenuListener(this);
        menu_contact.addMenuListener(this);
        menu_exit.addMenuListener(this);
        menu_item_login.addActionListener(this);
        menu_item_settings.addActionListener(this);
        menu_item_admin.addActionListener(this);
        menu_item_logout.addActionListener(this);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(menu_item_login)) {
            this.parent.dispose();
            SwingUtilities.invokeLater(() -> new LoginGUI(user));
        } else if (source.equals(menu_item_settings)) {
            Helper.showMessageDialog("Yakında!");
        } else if (source.equals(menu_item_admin)) {
            this.parent.dispose();
            SwingUtilities.invokeLater(() -> new OperatorGUI(user));
        } else if (source.equals(menu_item_logout)) {
            if (Helper.showConfirmDialog("Hesabınızdan çıkış yapıyorsunuz.")) {
                this.parent.dispose();
                User newUser = new Visitor(0, "Visitor", "", "", "", "visitor");
                SwingUtilities.invokeLater(() -> new SearchGUI(newUser));
            }
        }
    }

    @Override
    public void menuSelected(MenuEvent e) {
        Object source = e.getSource();
        if (source.equals(menu_search)) {
            MenuListener[] listeners = menu_search.getMenuListeners();
            for (MenuListener listener : listeners) {
                menu_search.removeMenuListener(listener);
            }

            this.parent.dispose();
            EventQueue.invokeLater(() -> {
                JFrame searchGUI = new SearchGUI(this.user);
                searchGUI.setVisible(true);
            });
        } else if (source.equals(menu_aboutUs)) {
            MenuListener[] listeners = menu_aboutUs.getMenuListeners();
            for (MenuListener listener : listeners) {
                menu_aboutUs.removeMenuListener(listener);
            }

            this.parent.dispose();
            EventQueue.invokeLater(() -> {
                JFrame aboutGUI = new AboutGUI(this.user);
                aboutGUI.setVisible(true);
            });
        } else if (source.equals(menu_contact)) {
            MenuListener[] listeners = menu_contact.getMenuListeners();
            for (MenuListener listener : listeners) {
                menu_contact.removeMenuListener(listener);
            }

            this.parent.dispose();
            EventQueue.invokeLater(() -> {
                JFrame contactGUI = new ContactGUI(this.user);
                contactGUI.setVisible(true);
            });
        } else if (source.equals(menu_exit)) {
            if (Helper.showConfirmDialog("sure")) {
                System.exit(0);
            }
        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }
}
