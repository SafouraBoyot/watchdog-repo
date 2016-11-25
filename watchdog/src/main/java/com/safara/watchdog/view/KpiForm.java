/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safara.watchdog.view;

/**
 *
 * @author safoura
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import com.safara.watchdog.biz.ClientContext;
import com.safara.watchdog.biz.ClientContextPool;
import com.safara.watchdog.common.LogUtil;
import com.safara.watchdog.dao.HeartBeatDao;
import com.safara.watchdog.entity.HeartBeatEntity;

class KpiForm
        extends BaseGui {

    private JTabbedPane tabbedPane;
    private JPanel cilentkpiPanel;
    private JPanel historyPanel;
    private JButton settingJButton;
    private JButton findJButton;
    private JTable clientList;
    private DefaultTableModel cilentModel;
    private JTable historyList;
    private DefaultTableModel historyModel;
    private JDatePickerImpl fromDatePicker;
    private JDatePickerImpl toDatePicker;
    private List<HeartBeatEntity> kpiHistories;
    private static Logger log;
    private Timer timer;

    public KpiForm() {
        log = new LogUtil(KpiForm.class.getName()).getLogger();
        initComponents();
    }

    @Override
    protected void initComponents() {
        setTitle("WatchDog Application");
        setSize(750, 400);
        setBackground(Color.gray);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        createPage1();
        createPage2();

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("ClientKPIList", cilentkpiPanel);
        tabbedPane.addTab("History", historyPanel);
        topPanel.add(tabbedPane, BorderLayout.CENTER);
    }

    public void createPage1() {
        cilentkpiPanel = new JPanel();
        cilentkpiPanel.setLayout(new BorderLayout());
        Map<String, ClientContext> clientContextPool = ClientContextPool.INSTANCE.getClientContextPool();

        String columnNames[] = {"Client IP", "Total Mem", "Free Mem",
            "Cpu Load", "Process Name", "Is Alive", "HasAlarm", "AlarmInfo"};
        String rowData[][] = new String[clientContextPool.size()][columnNames.length];

        if (clientContextPool != null && clientContextPool.size() != 0) {
            java.util.List<ClientContext> clientList
                    = new ArrayList<ClientContext>(clientContextPool.values());
            for (int i = 0; i < clientContextPool.size(); i++) {
                rowData[i][0] = clientList.get(i).getHeartBeatDto().getIp();
                rowData[i][1] = String.valueOf(clientList.get(i).getHeartBeatDto().getTotalMemory());
                rowData[i][2] = String.valueOf(clientList.get(i).getHeartBeatDto().getFreeMemory());
                rowData[i][3] = String.valueOf(clientList.get(i).getHeartBeatDto().getSystemCpuLoad());
                rowData[i][4] = String.valueOf(clientList.get(i).getHeartBeatDto().getProcessName());
                rowData[i][5] = String.valueOf(clientList.get(i).getHeartBeatDto().isProcessIsAlive());
                rowData[i][6] = String.valueOf(clientList.get(i).getHeartBeatDto().getHasAlarm());
                rowData[i][7] = String.valueOf(clientList.get(i).getHeartBeatDto().getAlarmInfo());
            }
        }

        cilentModel = new DefaultTableModel(rowData, columnNames);
        clientList = new JTable(cilentModel);
        JScrollPane scrollPane = new JScrollPane(getNewRenderedTable(clientList));
        cilentkpiPanel.add(scrollPane, BorderLayout.CENTER);

        settingJButton = new JButton("Setting");
        settingJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingJButtonActionPerformed(evt);
            }

        });
        cilentkpiPanel.add(settingJButton, BorderLayout.NORTH);
        ActionListener action = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Map<String, ClientContext> clientContextPool = ClientContextPool.INSTANCE.getClientContextPool();

                cilentModel = (DefaultTableModel) getNewRenderedTable(clientList).getModel();

                String columnNames[] = {"Client IP", "Total Mem", "Free Mem",
                    "Cpu Load", "Process Name", "Is Alive", "HasAlarm", "AlarmInfo"};
                String rowData[][] = new String[clientContextPool.size()][columnNames.length];

                if (clientContextPool != null && clientContextPool.size() != 0) {
                    java.util.List<ClientContext> clientList
                            = new ArrayList<ClientContext>(clientContextPool.values());
                    for (int i = 0; i < clientContextPool.size(); i++) {
                        rowData[i][0] = clientList.get(i).getHeartBeatDto().getIp();
                        rowData[i][1] = String.valueOf(clientList.get(i).getHeartBeatDto().getTotalMemory());
                        rowData[i][2] = String.valueOf(clientList.get(i).getHeartBeatDto().getFreeMemory());
                        rowData[i][3] = String.valueOf(clientList.get(i).getHeartBeatDto().getSystemCpuLoad());
                        rowData[i][4] = String.valueOf(clientList.get(i).getHeartBeatDto().getProcessName());
                        rowData[i][5] = String.valueOf(clientList.get(i).getHeartBeatDto().isProcessIsAlive());
                        rowData[i][6] = String.valueOf(clientList.get(i).getHeartBeatDto().getHasAlarm());
                        rowData[i][7] = String.valueOf(clientList.get(i).getHeartBeatDto().getAlarmInfo());

                    }
                }

                cilentModel = new DefaultTableModel(rowData, columnNames);
                clientList.setModel(cilentModel);
                cilentModel.fireTableDataChanged();
            
            }
        };
        timer = new Timer(8000, action);
        timer.setInitialDelay(0);
        timer.start();
        
    }

    private void settingJButtonActionPerformed(ActionEvent evt) {
        log.log(Level.INFO, "settingJButtonActionPerformed");
        int selectedRowIndex = clientList.getSelectedRow();
        log.log(Level.INFO, "selectedRowIndex = " + selectedRowIndex);
        if (selectedRowIndex != -1) {
            String clientIp = (String) clientList.getModel().getValueAt(selectedRowIndex, 0);
            log.log(Level.INFO, "clientIp = " + clientIp);
            String totalMem = (String) clientList.getModel().getValueAt(selectedRowIndex, 1);
            log.log(Level.INFO, "totalMem = " + totalMem);
            String freeMem = (String) clientList.getModel().getValueAt(selectedRowIndex, 2);
            log.log(Level.INFO, "freeMem = " + freeMem);
            String cpuLoad = (String) clientList.getModel().getValueAt(selectedRowIndex, 3);
            log.log(Level.INFO, "cpuLoad = " + cpuLoad);
            SettingForm settingForm = new SettingForm(clientIp, cpuLoad, freeMem, totalMem);

        }

    }

    public void createPage2() {
        kpiHistories = new ArrayList();
        historyPanel = new JPanel();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(historyPanel);
        historyPanel.setLayout(layout);

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JLabel fromJLabel = new JLabel("FromDate : ");
        UtilDateModel fromModel = new UtilDateModel();
        JDatePanelImpl fromDatePanel = new JDatePanelImpl(fromModel, p);
        fromDatePicker = new JDatePickerImpl(fromDatePanel, new DateLabelFormatter());

        JLabel toJLabel = new JLabel("ToDate : ");
        UtilDateModel toModel = new UtilDateModel();
        JDatePanelImpl toDatePanel = new JDatePanelImpl(toModel, p);
        toDatePicker = new JDatePickerImpl(toDatePanel, new DateLabelFormatter());

        findJButton = new JButton("Find");
        findJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findJButtonActionPerformed(evt);
            }

        });

        String columnNames[] = {"Client IP", "Total Mem", "Free Mem",
            "Cpu Load", "Process Name", "Is Alive", "HasAlarm", "AlarmInfo"};
        String rowData[][] = new String[kpiHistories.size()][columnNames.length];

        historyModel = new DefaultTableModel(rowData, columnNames);
        historyList = new JTable(historyModel);

        JScrollPane scrollPane = new JScrollPane(historyList);

        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(fromJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(fromDatePicker).
                                        addGap(10, 10, 10))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(toJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(toDatePicker)
                                        .addGap(10, 10, 10)))
                )
                .addGroup(layout.createSequentialGroup()
                        .addGap(600, 600, 600)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(findJButton)
                        )
                ).addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE))
                )
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(fromJLabel)
                                                .addComponent(fromDatePicker))
                                        .addGap(49, 49, 49)
                                        .addComponent(toJLabel))
                                .addComponent(toDatePicker))
                        .addGap(62, 62, 62)
                        .addComponent(findJButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                        .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))
        );

    }

    private void findJButtonActionPerformed(ActionEvent evt) {
        log.log(Level.INFO, "findJButtonActionPerformed");
        HeartBeatDao heartBeatDao = new HeartBeatDao();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fromSelectedDate = (Date) fromDatePicker.getModel().getValue();
            Date fromParsedDate = dateFormat.parse(dateFormat.format(fromSelectedDate));
            Timestamp fromDate = new java.sql.Timestamp(fromParsedDate.getTime());
            Date toSelectedDate = (Date) toDatePicker.getModel().getValue();
            Date toParsedDate = dateFormat.parse(dateFormat.format(toSelectedDate));
            Timestamp toDate = new java.sql.Timestamp(toParsedDate.getTime());
            kpiHistories = heartBeatDao.findByDateCriteria(fromDate, toDate);

            historyModel = (DefaultTableModel) historyList.getModel();

            String columnNames[] = {"Client IP", "Total Mem", "Free Mem",
                "Cpu Load", "Process Name", "Is Alive", "HasAlarm", "AlarmInfo"};
            String rowData[][] = new String[kpiHistories.size()][columnNames.length];

            if (kpiHistories != null && kpiHistories.size() != 0) {

                for (int i = 0; i < kpiHistories.size(); i++) {
                    rowData[i][0] = kpiHistories.get(i).getIp();
                    rowData[i][1] = String.valueOf(kpiHistories.get(i).getTotalMemory());
                    rowData[i][2] = String.valueOf(kpiHistories.get(i).getFreeMemory());
                    rowData[i][3] = String.valueOf(kpiHistories.get(i).getSystemCpuLoad());
                    rowData[i][4] = String.valueOf(kpiHistories.get(i).getProcessName());
                    rowData[i][5] = String.valueOf(kpiHistories.get(i).isProcessIsAlive());
                    rowData[i][6] = String.valueOf(kpiHistories.get(i).getHasAlarm());
                    if (kpiHistories.get(i).getAlarmInfo() != null) {
                        rowData[i][7] = String.valueOf(kpiHistories.get(i).getAlarmInfo());
                    } else {
                        rowData[i][7] = "";
                    }

                }
            }

            historyModel = new DefaultTableModel(rowData, columnNames);
            historyList.setModel(historyModel);
            historyModel.fireTableDataChanged();

        } catch (ParseException ex) {
            log.log(Level.SEVERE, "ParseException", ex);
            ex.printStackTrace();
        }

    }

    private static JTable getNewRenderedTable(final JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                String status = (String) table.getModel().getValueAt(row, 6);
                if ("true".equals(status)) {
                    setBackground(Color.RED);
                    setForeground(Color.WHITE);
                } else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }
                return this;
            }
        });
        return table;
    }
}
