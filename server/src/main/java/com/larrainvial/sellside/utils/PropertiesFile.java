package com.larrainvial.sellside.utils;

import com.larrainvial.trading.utils.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {

    private Properties properties;
    private static final String NAME = PropertiesFile.class.getSimpleName();

    public PropertiesFile(String url) {

        properties = new Properties();

        try {

            this.properties.load(new FileInputStream(url));

        } catch (IOException e) {
            Logger.error(NAME, "Exception: " + NAME, e);
        }
    }

    public String getPropertiesString(String key) {
        return properties.getProperty(key);
    }

}




