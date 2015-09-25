package com.larrainvial.killprocess;


import com.larrainvial.killprocess.util.PropertiesFile;
import com.larrainvial.killprocess.vo.CoreVO;

import java.util.HashMap;

public class Repository {

    public static PropertiesFile killProcess;
    public static HashMap<String, CoreVO> coreStrategy = new HashMap<String, CoreVO>();
    public static HashMap<String, CoreVO> weborbStrategy = new HashMap<String, CoreVO>();
}
