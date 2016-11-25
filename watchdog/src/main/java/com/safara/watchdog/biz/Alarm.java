package com.safara.watchdog.biz;

import com.safara.watchdog.common.LogUtil;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by safoura on 1/15/16.
 */
public class Alarm extends Thread {

    private static final Logger log = new LogUtil(Alarm.class.getName()).getLogger();
    private Thread thread;
    private String threadName;
    private Long lastHeartBeatTime;
    private Long heartBeatReceivedTime;
    public static Boolean isRunning;
    public static final Boolean monitorObject = true;

    public Alarm(String threadName) {
        this.threadName = threadName;
        this.thread = new Thread(this);
        this.isRunning = true;
    }

    @Override
    public void run() {

        log.log(Level.INFO, "HeartBeatFailureDetector.run()");
        while (!thread.isInterrupted()) {

            synchronized (monitorObject) {
                try {
                    while (isRunning) {
                        System.out.println("wait");
                        calculateHeartBeat(heartBeatReceivedTime, lastHeartBeatTime);
                        this.lastHeartBeatTime = heartBeatReceivedTime;
                        isRunning = false;
                        monitorObject.wait();

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void start(Long heartBeatReceivedTime) {
        this.lastHeartBeatTime = heartBeatReceivedTime;
        this.heartBeatReceivedTime = heartBeatReceivedTime;
        thread.start();
    }

    private void calculateHeartBeat(Long heartBeatReceivedTime, Long lastHeartBeatTime) {
        System.out.println("calculateHeartBeat");
        long l = heartBeatReceivedTime - lastHeartBeatTime;
        long difference = TimeUnit.MILLISECONDS.toSeconds(l);
        if (difference > 10) {
            System.out.println("HeatBeatFailure....");
        }
    }

    public Long getHeartBeatReceivedTime() {
        return heartBeatReceivedTime;
    }

    public void setHeartBeatReceivedTime(Long heartBeatReceivedTime) {
        this.heartBeatReceivedTime = heartBeatReceivedTime;
    }
}
