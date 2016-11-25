/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safara.watchdog.common;

/**
 *
 * @author safoura
 */
public class AlarmSetting {
    private String cpuLoad;
    private String freeMem;

    
    public String getCpuLoad() {
        return cpuLoad;
    }

    public void setCpuLoad(String cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    public String getFreeMem() {
        return freeMem;
    }

    public void setFreeMem(String freeMem) {
        this.freeMem = freeMem;
    }

    @Override
    public String toString() {
        return "AlarmSetting{" + "cpuLoad=" + cpuLoad + ", freeMem=" + freeMem + '}';
    }
    
    
    
    
}
