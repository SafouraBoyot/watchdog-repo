package com.safara.watchdog.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by safoura
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Heartbeat implements Serializable {


    private String ip;
    private Long time;
    private Long totalMemory;
    private Long freeMemory;
    private Double systemCpuLoad;
    private String processName;
    private boolean processIsAlive;
    private String info;//Todo : make enum for this !

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
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

    @Override
    public String toString() {
        return "Heartbeat{" + "ip=" + ip
                + ", time=" + time
                + ", totalMemory=" + totalMemory
                + ", freeMemory=" + freeMemory
                + ", systemCpuLoad=" + systemCpuLoad
                + ", processName=" + processName
                + ", processIsAlive=" + processIsAlive
                + ", info=" + info + '}';
    }
}
