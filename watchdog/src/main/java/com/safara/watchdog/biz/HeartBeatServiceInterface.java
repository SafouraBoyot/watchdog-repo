/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safara.watchdog.biz;

import com.safara.watchdog.dto.HeartBeatDto;

/**
 *
 * @author safoura
 */
public interface HeartBeatServiceInterface {

    public void doHeartBeat(HeartBeatDto heartBeatDto);
}
