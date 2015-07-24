package com.larrainvial.sellside.utils;

import com.larrainvial.trading.utils.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class PropertiesFile {

    private Properties properties;
    private static final String NAME = PropertiesFile.class.getSimpleName();

    public PropertiesFile(URL url) {

        properties = new Properties();

        try {

            this.properties.load(url.openStream());

        } catch (IOException e) {
            Logger.error(NAME, "Exception: " + NAME, e);
        }
    }

    public String getPropertiesString(String key) {
        return properties.getProperty(key);
    }

}




