package com.larrainvial.process;


import com.larrainvial.process.util.PropertiesFile;
import com.larrainvial.process.vo.CoreVO;

import java.util.LinkedHashMap;

public class Repository {

    public static PropertiesFile killProcess;
    public static LinkedHashMap<String, CoreVO> coreStrategy = new LinkedHashMap<String, CoreVO>();
    public static LinkedHashMap<String, CoreVO> weborbStrategy = new LinkedHashMap<String, CoreVO>();
}
