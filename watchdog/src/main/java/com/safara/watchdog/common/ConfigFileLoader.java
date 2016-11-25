/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safara.watchdog.common;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;

/**
 *
 * @author safoura
 */
public class ConfigFileLoader {

    private String fileName;
    private Properties propertiesConfiguration;

    public ConfigFileLoader(String fileName) {
        this.fileName = fileName;
        try {
            load();
        } catch (IOException ex) {
            Logger.getLogger(ConfigFileLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void load() throws IOException {
        propertiesConfiguration = new Properties();

        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            propertiesConfiguration.load(loader.getResourceAsStream(fileName));
        } catch (Exception e) {
            throw new IIOException(e.getMessage());
        }
    }

    public String getValue(String key) {
        return (String) propertiesConfiguration.getProperty(key);
    }

    public String getValue(String key, String defaults) {
        return propertiesConfiguration.getProperty(getValue(key), defaults);
    }
}
