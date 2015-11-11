package com.larrainvial.process.util;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

public class PropertiesFile {

    public Properties properties;
    private static Logger logger = Logger.getLogger(PropertiesFile.class.getName());

    public PropertiesFile(String url) {

        properties = new Properties();

        try {

            this.properties.load(new FileInputStream(url));

        } catch (IOException ex) {
            logger.error(Level.SEVERE, ex);
        }
    }

    public String getPropertiesString(String key) {
        return properties.getProperty(key);
    }

}




