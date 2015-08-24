package com.larrainvial.latency;


import com.larrainvial.latency.utils.FileRepository;

import java.util.HashMap;
import java.util.Map;

public class  Repository {

    public static HashMap<String, Algo> strategy = new HashMap<String, Algo>();
    public static Map<String, String> directoryFile = new HashMap<String, String>();
    public static FileRepository nameFile;
    public static FileRepository nameFileTSM;
    public static String FileLog;

}
