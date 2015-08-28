package com.larrainvial.logviwer.utils;

import com.larrainvial.trading.utils.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

public class PropertiesFile {

    private Properties properties;
    private static final String NAME = PropertiesFile.class.getSimpleName();

    public PropertiesFile(URL url) {

        properties = new Properties();
        OutputStream output = null;

        try {

            this.properties.load(url.openStream());
            //output = new FileOutputStream(url.openStream());


        } catch (IOException e) {
            Logger.error(NAME, "Exception: " + NAME, e);
        }
    }

    public String getPropertiesString(String key) {
        return properties.getProperty(key);
    }

    public void setPropertiesString(String key, String value) {
         properties.setProperty(key, value);
         //properties.store(output, null);
    }

}




