package com.larrainvial.sellside.utils;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;

public class PropertiesFile {

    private Properties properties;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public PropertiesFile(String url) {

        properties = new Properties();

        try {

            this.properties.load(new FileInputStream(url));

        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
        }
    }

    public String getPropertiesString(String key) {
        return properties.getProperty(key);
    }

}




