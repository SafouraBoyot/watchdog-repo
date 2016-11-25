/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safara.watchdog.biz;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.safara.watchdog.common.HeartBeatAdapter;
import com.safara.watchdog.common.HeartbeatInfoEnum;
import com.safara.watchdog.common.LogUtil;
import com.safara.watchdog.dao.HeartBeatDao;
import com.safara.watchdog.dto.HeartBeatDto;
import com.safara.watchdog.entity.HeartBeatEntity;
import com.safara.watchdog.exception.HeartBeatDaoException;

/**
 *
 * @author safoura
 */
public class HeartBeatService implements HeartBeatServiceInterface {
    private static Logger log;

    public HeartBeatService() {
         log = new LogUtil(HeartBeatService.class.getName()).getLogger();
    }
    
    @Override
    public void doHeartBeat(HeartBeatDto heartBeatDto) {
        HeartBeatDao heartBeatDao = new HeartBeatDao();
        String clientIp = heartBeatDto.getIp();
        if (!ClientContextPool.INSTANCE.getClientContextPool().containsKey(clientIp)
                && !heartBeatDto.getInfo().equals(HeartbeatInfoEnum.DOWN.getValue())) {
            try {
                ClientContextPool.INSTANCE.register(clientIp, new ClientContext(heartBeatDto));
                HeartBeatEntity heartBeatEntity = HeartBeatAdapter.heartBeatEntityConverter(heartBeatDto);
                heartBeatDao.save(heartBeatEntity);
            } catch (HeartBeatDaoException ex) {
           log.log(Level.SEVERE, null, ex);
            }
        } else if (!heartBeatDto.getInfo().equals(HeartbeatInfoEnum.DOWN.getValue())) {
            synchronized (HeartBeatFailureDetector.monitorObject) {

                HeartBeatFailureDetector heartBeatFailureDetector
                        = ClientContextPool.INSTANCE.getClientContextPool().get(clientIp).getHeartBeatFailureDetector();
                heartBeatFailureDetector.setHeartBeatReceivedTime(heartBeatDto.getTime().getTime());
                heartBeatFailureDetector.setHeartBeatDto(heartBeatDto);
                heartBeatFailureDetector.isRunning = true;
                heartBeatFailureDetector.monitorObject.notifyAll();
              

            }
        } else if (heartBeatDto.getInfo().equals(HeartbeatInfoEnum.DOWN.getValue())) {
            try {
                ClientContextPool.INSTANCE.unRegister(clientIp);
                HeartBeatEntity heartBeatEntity = HeartBeatAdapter.heartBeatEntityConverter(heartBeatDto);
                heartBeatDao.save(heartBeatEntity);
            } catch (HeartBeatDaoException ex) {
                Logger.getLogger(HeartBeatService.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
