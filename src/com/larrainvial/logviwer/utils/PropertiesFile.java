package com.larrainvial.logviwer.utils;

import org.apache.log4j.Logger;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;

public class PropertiesFile {

    public Properties properties;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    public OutputStream output = null;
    private URL url;

    public PropertiesFile(String url) {

        try {
            properties = new Properties();
            properties.load(new FileInputStream(url));


        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }
    }

    public String getPropertiesString(String key) {
        return properties.getProperty(key);
    }

    public void setPropertiesString(String key, String value) {

        try {

            output = new FileOutputStream(url.getPath());
            properties.setProperty(key, value);
            properties.store(output, null);
            output.close();

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

}




