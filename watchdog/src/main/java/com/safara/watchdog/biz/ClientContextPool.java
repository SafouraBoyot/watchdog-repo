package com.safara.watchdog.biz;

/**
 * safoura
 */
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.safara.watchdog.common.AlarmSetting;
import com.safara.watchdog.common.LogUtil;
import com.safara.watchdog.dto.HeartBeatDto;

public enum ClientContextPool {

    INSTANCE;

    private Map<String, ClientContext> clientContextPool = new ConcurrentHashMap();
    private Map<String, AlarmSetting> alarmSetting = new ConcurrentHashMap();
    private static Logger log = new LogUtil(ClientContextPool.class.getName()).getLogger();

    ;

    public void register(String name, ClientContext clientContext) {
//        clientContainerPool.putIfAbsent(name, clientContext);//jdk8
        if (!clientContextPool.containsKey(name)) {//jdk7
            clientContextPool.put(name, clientContext);
        }
        clientContext.startContext();
    }

    public void unRegister(String name) {
        log.log(Level.INFO, "ClientContextPool.unRegister");

        if (clientContextPool.get(name) != null) {
            clientContextPool.remove(name);
            ClientContextPool.INSTANCE.getAlarmSetting().remove(name);
        } else {
            log.log(Level.INFO, "Client Context Already unregistered!!!!");

        }
    }

    public Map<String, ClientContext> getClientContextPool() {
        return clientContextPool;
    }

    public void setClientContext(String clientId, HeartBeatDto heartBeatDto) {
        if (clientContextPool.get(clientId)!=null) {
             clientContextPool.get(clientId).setHeartBeatDto(heartBeatDto);
        }
       
    }

    public Map<String, AlarmSetting> getAlarmSetting() {
        return alarmSetting;
    }

    public void setAlarmSetting(Map<String, AlarmSetting> alarmSetting) {
        this.alarmSetting = alarmSetting;
    }

}
