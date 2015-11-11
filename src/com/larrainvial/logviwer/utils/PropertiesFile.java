package com.larrainvial.logviwer.utils;

import com.larrainvial.trading.utils.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

public class PropertiesFile {

    private Properties properties;
    private static final String NAME = PropertiesFile.class.getSimpleName();
    private OutputStream output = null;
    private URL url;


    public PropertiesFile(String url) {

        try {
            //this.url = url;
            properties = new Properties();
            properties.load(new FileInputStream(url));


        } catch (IOException e) {
            Logger.error(NAME, "Exception: " + NAME, e);
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




