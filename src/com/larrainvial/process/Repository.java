package com.larrainvial.process;


import com.larrainvial.process.model.ModelProcess;
import com.larrainvial.process.util.PropertiesFile;
import java.util.LinkedHashMap;

public class Repository {

    public static PropertiesFile killProcess;
    public static LinkedHashMap<String, ModelProcess> coreStrategy = new LinkedHashMap<String, ModelProcess>();
    public static LinkedHashMap<String, ModelProcess> weborbStrategy = new LinkedHashMap<String, ModelProcess>();
}
