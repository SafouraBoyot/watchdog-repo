package com.safara.watchdog.biz;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.safara.watchdog.common.AlarmSetting;
import com.safara.watchdog.common.HeartBeatAdapter;
import com.safara.watchdog.common.LogUtil;
import com.safara.watchdog.dao.HeartBeatDao;
import com.safara.watchdog.dto.HeartBeatDto;
import com.safara.watchdog.entity.HeartBeatEntity;
import com.safara.watchdog.exception.HeartBeatDaoException;

/**
 * @author safoura
 */
public class HeartBeatFailureDetector extends Thread {

    private static final Logger log = new LogUtil(HeartBeatFailureDetector.class.getName()).getLogger();
    private Thread thread;
    private String threadName;
    private Long lastHeartBeatTime;
    private Long heartBeatReceivedTime;
    public static Boolean isRunning;
    public static final Boolean monitorObject = true;
    private HeartBeatDto heartBeatDto;

    public HeartBeatFailureDetector(String threadName) {
        this.threadName = threadName;
        this.thread = new Thread(this);
        this.isRunning = true;
    }

    public HeartBeatFailureDetector() {

    }

    @Override
    public void run() {

        log.log(Level.INFO, "HeartBeatFailureDetector.run()");
        HeartBeatDao heartBeatDao = new HeartBeatDao();
        while (!thread.isInterrupted()) {

            synchronized (monitorObject) {
                try {
                    while (isRunning) {

                        calculateHeartBeat(heartBeatReceivedTime, lastHeartBeatTime);
                        this.lastHeartBeatTime = heartBeatReceivedTime;
                        if (!ClientContextPool.INSTANCE.getAlarmSetting().isEmpty()) {

                            AlarmSetting setting = ClientContextPool.INSTANCE.getAlarmSetting().get(heartBeatDto.getIp());
                            if (setting !=null) {
                                  kpiMonitoring(Double.valueOf(setting.getCpuLoad()), Long.valueOf(setting.getFreeMem())); 
                            }
                        }
                        ClientContextPool.INSTANCE.setClientContext(heartBeatDto.getIp(), heartBeatDto);
                        HeartBeatEntity heartBeatEntity = HeartBeatAdapter.heartBeatEntityConverter(heartBeatDto);
                        heartBeatDao.save(heartBeatEntity);
                        isRunning = false;
                        log.log(Level.INFO, "HeartBeatFailureDetector.wait");
                        monitorObject.wait();

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (HeartBeatDaoException ex) {
                    log.log(Level.SEVERE, "HeartBeatFailureDetector.HeartBeatDaoException", ex);
                }
            }

        }
    }

    public void start(HeartBeatDto heartBeatDto) {
        this.heartBeatDto = heartBeatDto;
        this.lastHeartBeatTime = heartBeatDto.getTime().getTime();
        this.heartBeatReceivedTime = heartBeatDto.getTime().getTime();
        thread.start();
    }

    private void calculateHeartBeat(Long heartBeatReceivedTime, Long lastHeartBeatTime) {

        log.log(Level.INFO, "HeartBeatFailureDetector.calculateHeartBeat");
        long l = heartBeatReceivedTime - lastHeartBeatTime;
        long difference = TimeUnit.MILLISECONDS.toSeconds(l);
        if (difference > 10) {

            log.log(Level.INFO, "HeartBeatFailureDetector.HeatBeatFailure!");
            heartBeatDto.setHasAlarm(true);
            heartBeatDto.setAlarmInfo("HeatBeatFailure");
        }
    }

    private void kpiMonitoring(Double systemCpuLoad, Long freeMemory) {

        log.log(Level.INFO, "HeartBeatFailureDetector.kpiMonitoring");

        if (heartBeatDto.getSystemCpuLoad() > systemCpuLoad || heartBeatDto.getFreeMemory() > freeMemory) {
            log.log(Level.INFO, "HeartBeatFailureDetector.KpiAlarm");
            heartBeatDto.setHasAlarm(true);
            heartBeatDto.setAlarmInfo("KpiAlarm");

        }
    }

    public Long getHeartBeatReceivedTime() {
        return heartBeatReceivedTime;
    }

    public void setHeartBeatReceivedTime(Long heartBeatReceivedTime) {
        this.heartBeatReceivedTime = heartBeatReceivedTime;
    }

    public HeartBeatDto getHeartBeatDto() {
        return heartBeatDto;
    }

    public void setHeartBeatDto(HeartBeatDto heartBeatDto) {
        this.heartBeatDto = heartBeatDto;
    }

}
