package com.safara.watchdog.view;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import com.safara.watchdog.biz.ClientContextPool;
import com.safara.watchdog.common.AlarmSetting;

/**
 *
 * @author safoura
 */
public class SettingForm extends BaseGui {

    private String clientIp;
    private String cpuLoad;
    private String freeMem;
    private String totalMem;
    private JFrame frame;
    private JLabel cpuJLabel;
    private JLabel memJLabel;
    private JButton okJbutton;
    private JComboBox cpuComboBox;
    private JComboBox freeMemComboBox;

    public SettingForm(String clientIp, String cpuLoad, String freeMem, String totalMem) {
        this.clientIp = clientIp;
        this.cpuLoad = cpuLoad;
        this.freeMem = freeMem;
        this.totalMem = totalMem;

        initComponents();
    }

    @Override
    protected void initComponents() {

        frame = new JFrame();


        String cpulabels[]
                = {"+0%", "+20%", "+40%", "+60%",
                    "+80%", "+100%"};


        String memlabels[]
                = {"+0%", "+20%", "+40%", "+60%",
                    "+80%", "+100%"};

        frame = new JFrame("Setting");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentpane = frame.getContentPane();
        contentpane.setLayout(new GridLayout(5, 1));
        cpuJLabel = new JLabel("CpuLoad : ");
        cpuComboBox = new JComboBox(cpulabels);
        cpuComboBox.setMaximumRowCount(6);
        contentpane.add(cpuJLabel);
        contentpane.add(cpuComboBox);

        freeMemComboBox = new JComboBox(memlabels);
        freeMemComboBox.setMaximumRowCount(6);
        memJLabel = new JLabel("FreeMemory : ");
        contentpane.add(memJLabel);
        contentpane.add(freeMemComboBox);

        okJbutton = new JButton("OK");
        contentpane.add(okJbutton);
        okJbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okJButtonActionPerformed(evt);
            }
        });

        frame.setSize(600, 150);
        frame.setVisible(true);
        pack();

    }

    private void okJButtonActionPerformed(ActionEvent evt) {
        AlarmSetting alarmSetting = new AlarmSetting();
        System.out.println("cpuComboBox.getSelectedItem().toString() = " + cpuComboBox.getSelectedItem().toString());
        alarmSetting.setCpuLoad(String.valueOf(calculateCpuSetting(cpuComboBox.getSelectedItem().toString())));

        System.out.println("freeMemComboBox.getSelectedItem() = " + freeMemComboBox.getSelectedItem());
        alarmSetting.setFreeMem(String.valueOf(calculateFreeMemSetting(freeMemComboBox.getSelectedItem().toString())));
        System.out.println(alarmSetting);
        ClientContextPool.INSTANCE.getAlarmSetting().put(clientIp, alarmSetting);
        frame.dispose();

    }

    private long calculateFreeMemSetting(String percent) {
 
        long memLabel = Long.valueOf(freeMem);
        if (percent.contains("+10%")) {
            return (long) (memLabel + (memLabel * 0.1));
        } else if (percent.equals("+20%")) {
            return (long) (memLabel + (memLabel * 0.2));
        } else if (percent.equals("+40%")) {
            return (long) (memLabel + (memLabel * 0.4));
        } else if (percent.equals("+60%")) {
            return (long) (memLabel + (memLabel * 0.6));
        } else if (percent.equals("+80%")) {
            return (long) (memLabel + (memLabel * 0.8));
        } else if (percent.equals("+100%")) {
            return (long) (memLabel + (memLabel * 1));
        } else if (percent.equals("+0%")) {
            return (long) (memLabel + (memLabel * 0));
        }
        return 0;
    }

    private double calculateCpuSetting(String percent) {
        double cpuLabel = Double.valueOf(cpuLoad);
        if (percent.contains("+10%")) {
            return cpuLabel + (cpuLabel * 0.1);
        } else if (percent.equals("+20%")) {
            return cpuLabel + (cpuLabel * 0.2);
        } else if (percent.equals("+40%")) {
            return cpuLabel + (cpuLabel * 0.4);
        } else if (percent.equals("+60%")) {
            return cpuLabel + (cpuLabel * 0.6);
        } else if (percent.equals("+80%")) {
            return cpuLabel + (cpuLabel * 0.8);
        } else if (percent.equals("+100%")) {
            return cpuLabel + (cpuLabel * 1);
        } else if (percent.equals("+0%")) {
            return cpuLabel + (cpuLabel * 0);
        }
        return 0;
    }

}
