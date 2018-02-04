package com.kevingentile.com;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client {
    private JButton button1;
    private JPanel MainPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Fortnite Uptime Tracker");
        frame.setContentPane(new Client().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Client() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Hello World!");
            }
        });
    }
}
