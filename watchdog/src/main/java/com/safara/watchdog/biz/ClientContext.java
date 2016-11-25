package com.safara.watchdog.biz;

import com.safara.watchdog.common.Heartbeat;
import com.safara.watchdog.dao.HeartBeatDao;
import com.safara.watchdog.dto.HeartBeatDto;

/**
 * Created by safoura
 */
public class ClientContext {

    final ThreadGroup heartBeatThreadGroup;

    private String ip;
    private HeartBeatDto heartBeatDto;
    private final HeartBeatFailureDetector heartBeatFailureDetector;

    public ClientContext(HeartBeatDto heartBeatDto) {
        this.heartBeatDto = heartBeatDto;
        this.ip = heartBeatDto.getIp();
        this.heartBeatFailureDetector = new HeartBeatFailureDetector(heartBeatDto.getProcessName() + heartBeatDto.getIp());
        this.heartBeatThreadGroup = new ThreadGroup("heartBeat");
    }

    public void startContext() {
        try {
            heartBeatFailureDetector.start(heartBeatDto);
            //alarm Thread start
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HeartBeatFailureDetector getHeartBeatFailureDetector() {
        return heartBeatFailureDetector;
    }
    

    public HeartBeatDto getHeartBeatDto() {
        return heartBeatDto;
    }

    public void setHeartBeatDto(HeartBeatDto heartBeatDto) {
        this.heartBeatDto = heartBeatDto;
    }

}
