package com.safara.watchdog.dto;

import java.sql.Timestamp;

/**
 * Created by safoura
 */
public class HeartBeatDto {

    private String ip;
    private Timestamp time;
    private Long totalMemory;
    private Long freeMemory;
    private Double systemCpuLoad;
    private String processName;
    private boolean processIsAlive;
    private String info;
    private Boolean hasAlarm;
    private String alarmInfo ="";

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Boolean getHasAlarm() {
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
        return "HeartBeatDto{" + "ip=" + ip + ", time=" + time
                + ", totalMemory=" + totalMemory + ", freeMemory="
                + freeMemory + ", systemCpuLoad=" + systemCpuLoad
                + ", processName=" + processName + ", processIsAlive="
                + processIsAlive + ", info=" + info + ", hasAlarm="
                + hasAlarm + ", alarmInfo=" + alarmInfo + '}';
    }

}
