package com.safara.watchdog.common;

import java.sql.Timestamp;
import com.safara.watchdog.common.Heartbeat;
import com.safara.watchdog.dto.HeartBeatDto;
import com.safara.watchdog.entity.HeartBeatEntity;

/**
 * Created by safoura
 */
public class HeartBeatAdapter {

    public static HeartBeatEntity heartBeatEntityConverter(HeartBeatDto heartBeatDto) {
        HeartBeatEntity heartbeatEntity = new HeartBeatEntity();
//        heartbeatEntity.setId(heartBeatDto.getId());
        heartbeatEntity.setIp(heartBeatDto.getIp());
        heartbeatEntity.setProcessName(heartBeatDto.getProcessName());
        heartbeatEntity.setFreeMemory(heartBeatDto.getFreeMemory());
        heartbeatEntity.setTotalMemory(heartBeatDto.getTotalMemory());
        heartbeatEntity.setSystemCpuLoad(heartBeatDto.getSystemCpuLoad());
        heartbeatEntity.setTime(heartBeatDto.getTime());
        heartbeatEntity.setProcessIsAlive(heartBeatDto.isProcessIsAlive());
        if (heartBeatDto.getHasAlarm().equals(true)) {
            heartbeatEntity.setHasAlarm(true);
        }
        if(heartBeatDto.getAlarmInfo()!=null){
            heartbeatEntity.setAlarmInfo(heartBeatDto.getAlarmInfo());
        }
        
        return heartbeatEntity;
    }

    public static HeartBeatDto heartBeatDtoAdapter(Heartbeat heartBeat) {
        HeartBeatDto heartBeatDto = new HeartBeatDto();
        heartBeatDto.setIp(heartBeat.getIp());
        heartBeatDto.setProcessName(heartBeat.getProcessName());
        heartBeatDto.setFreeMemory(heartBeat.getFreeMemory());
        heartBeatDto.setTotalMemory(heartBeat.getTotalMemory());
        heartBeatDto.setSystemCpuLoad(heartBeat.getSystemCpuLoad());
        heartBeatDto.setTime(new Timestamp(heartBeat.getTime()));
        heartBeatDto.setProcessIsAlive(heartBeat.isProcessIsAlive());
        heartBeatDto.setInfo(heartBeat.getInfo());
        heartBeatDto.setHasAlarm(false);
        return heartBeatDto;
    }
}
