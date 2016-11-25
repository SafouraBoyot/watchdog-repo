package com.safara.watchdog.common;

import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

/**
 *
 * @author safoura
 */
public class LogUtil {

    private String clazzName;
    private Logger logger;
    private FileHandler fh;
    private int limit;
    private int count;
    private boolean append;
    private Object object;
    private String handlerName;

    public LogUtil(String clazzName) {
        ConfigFileLoader configFileLoader = new ConfigFileLoader("logger.properties");
        limit = Integer.parseInt(configFileLoader.getValue("limit"));
        count = Integer.parseInt(configFileLoader.getValue("count"));
        append = Boolean.parseBoolean(configFileLoader.getValue("append"));
        handlerName = configFileLoader.getValue("handlers");

        this.clazzName = clazzName;
    }

    public Logger getLogger() {
        logger = Logger.getLogger(LogUtil.class.getName());
        try {
            if (handlerName.equals("java.util.logging.FileHandler")) {
//                fh = (FileHandler) Class.forName(handlerName).newInstance();
                fh = new FileHandler("Log/"+clazzName + new Date().toString() + ".log", append);
            }

        } catch (IOException ex) {
            Logger.getLogger(LogUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(LogUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        logger.addHandler(fh);
        return logger;
    }

}
