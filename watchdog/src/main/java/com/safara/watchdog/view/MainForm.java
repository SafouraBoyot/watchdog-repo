/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safara.watchdog.view;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import com.safara.watchdog.biz.NonBlockingHeartBeatServer;
import com.safara.watchdog.common.LogUtil;

/**
 *
 * @author safoura
 */
public class MainForm extends BaseGui {

    private static Logger log;
    private JButton startJButton;
    private JLabel startServerJLabel;

    public MainForm() {
        log = new LogUtil(MainForm.class.getName()).getLogger();
        initComponents();
    }

    @Override
    protected void initComponents() {
        log.log(Level.INFO, "MainForm.initComponents");
        startServerJLabel = new JLabel();
        startJButton = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        startServerJLabel.setText("             Server initialized!  ");
        startJButton.setText("start");
        startJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(startJButton)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(81, Short.MAX_VALUE)
                        .addComponent(startServerJLabel, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(startServerJLabel, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(startJButton)
                        .addContainerGap(64, Short.MAX_VALUE))
        );

        pack();

    }

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {
        log.log(Level.INFO, "MainForm.startButtonActionPerformed");
        this.dispose();
        KpiForm kpiform = new KpiForm();
        kpiform.setVisible(true);

    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);

            }
        });
        NonBlockingHeartBeatServer.startServer();

    }

}
