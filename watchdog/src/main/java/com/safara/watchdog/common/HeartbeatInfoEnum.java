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
public enum HeartbeatInfoEnum {

    UP(0, "up"),DOWN(1,"down");

    Integer ordinal;
    String value;

    private HeartbeatInfoEnum(Integer ordinal,String value ) {
        this.ordinal = ordinal;
        this.value = value;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public String getValue() {
        return value;
    }


}
