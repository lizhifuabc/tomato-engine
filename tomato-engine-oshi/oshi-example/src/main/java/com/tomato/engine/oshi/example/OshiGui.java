/*
 * Copyright 2020-2022 The OSHI Project Contributors
 * SPDX-License-Identifier: MIT
 */
package com.tomato.engine.oshi.example;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.tomato.engine.oshi.example.gui.*;
import oshi.SystemInfo;

/**
 * Basic Swing class to demonstrate potential uses for OSHI in a monitoring GUI. Not ready for production use and
 * intended as inspiration/examples.
 */
public class OshiGui {

    private JFrame mainFrame;
    private JButton jMenu;

    private SystemInfo si = new SystemInfo();

    public static void main(String[] args) {

        // 获取用户语言环境（例如从系统属性或配置中获取）
        Locale locale = Locale.getDefault(); // 默认语言环境
        // 或者手动指定语言环境
        // Locale locale = new Locale("zh", "CN");

        // 加载资源文件
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

        OshiGui gui = new OshiGui();
        gui.init();
        SwingUtilities.invokeLater(gui::setVisible);
    }

    private void setVisible() {
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jMenu.doClick();
    }

    private void init() {
        // Create the external frame
        mainFrame = new JFrame(Config.GUI_TITLE);
        mainFrame.setSize(Config.GUI_WIDTH, Config.GUI_HEIGHT);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(true);
        mainFrame.setLocationByPlatform(true);
        mainFrame.setLayout(new BorderLayout());
        // Add a menu bar
        JMenuBar menuBar = new JMenuBar();
        mainFrame.setJMenuBar(menuBar);
        // Assign the first menu option to be clicked on visibility
        jMenu = getJMenu("OS & HW Info", 'O', "Hardware & OS Summary", new OsHwTextPanel(si));
        menuBar.add(jMenu);
        // Add later menu items
        menuBar.add(getJMenu("Memory", 'M', "Memory Summary", new MemoryPanel(si)));
        menuBar.add(getJMenu("CPU", 'C', "CPU Usage", new ProcessorPanel(si)));
        menuBar.add(getJMenu("FileStores", 'F', "FileStore Usage", new FileStorePanel(si)));
        menuBar.add(getJMenu("Processes", 'P', "Processes", new ProcessPanel(si)));
        menuBar.add(getJMenu("USB Devices", 'U', "USB Device list", new UsbPanel(si)));
        menuBar.add(getJMenu("Network", 'N', "Network Params and Interfaces", new InterfacePanel(si)));
    }

    private JButton getJMenu(String title, char mnemonic, String toolTip, OshiJPanel panel) {
        JButton button = new JButton(title);
        button.setMnemonic(mnemonic);
        button.setToolTipText(toolTip);
        button.addActionListener(e -> {
            Container contentPane = this.mainFrame.getContentPane();
            if (contentPane.getComponents().length <= 0 || contentPane.getComponent(0) != panel) {
                resetMainGui();
                this.mainFrame.getContentPane().add(panel);
                refreshMainGui();
            }
        });

        return button;
    }

    private void resetMainGui() {
        this.mainFrame.getContentPane().removeAll();
    }

    private void refreshMainGui() {
        this.mainFrame.revalidate();
        this.mainFrame.repaint();
    }
}
