package com.safara.watchdog.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by safoura
 */
@Entity
@Table(name = "t_heartbeat")
public class HeartBeatEntity {

 
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "heartbeat_seq")
    @SequenceGenerator(name = "heartbeat_seq", sequenceName = "heartbeat_sequence", initialValue = 1, allocationSize = 1000000)
    @Column(name = "id")
    private int id;
    @Column(name = "ip")
    private String ip;
    @Column(name = "time")
    private Timestamp time;
    @Column(name = "totalmemory")
    private Long totalMemory;
    @Column(name = "freememory")
    private Long freeMemory;
    @Column(name = "systemcpuload")
    private Double systemCpuLoad;
    @Column(name = "processname")
    private String processName;
    @Column(name = "processisalive")
    private boolean processIsAlive;
    @Column(name = "hasAlarm")
    private boolean hasAlarm;
    @Column(name = "alarmInfo")
    private String alarmInfo ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(Long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public Long getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(Long freeMemory) {
        this.freeMemory = freeMemory;
    }

    public Double getSystemCpuLoad() {
        return systemCpuLoad;
    }

    public void setSystemCpuLoad(Double systemCpuLoad) {
        this.systemCpuLoad = systemCpuLoad;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public boolean isProcessIsAlive() {
        return processIsAlive;
    }

    public void setProcessIsAlive(boolean processIsAlive) {
        this.processIsAlive = processIsAlive;
    }

    public boolean getHasAlarm() {
        return hasAlarm;
    }

    public void setHasAlarm(boolean hasAlarm) {
        this.hasAlarm = hasAlarm;
    }

    public String getAlarmInfo() {
        return alarmInfo;
    }

    public void setAlarmInfo(String alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    @Override
    public String toString() {
        return "HeartBeatEntity{" + "id=" + id + ", ip=" + ip + ", time=" + time
                + ", totalMemory=" + totalMemory + ", freeMemory=" + freeMemory
                + ", systemCpuLoad=" + systemCpuLoad + ", processName=" + processName
                + ", processIsAlive=" + processIsAlive + ", hasAlarm=" + hasAlarm
                + ", alarmInfo=" + alarmInfo + '}';
    }

}
