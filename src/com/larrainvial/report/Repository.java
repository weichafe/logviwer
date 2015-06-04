package com.larrainvial.report;


import com.larrainvial.report.po.reportPO;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Repository {


    public static Map<String, reportPO> reportHashmap = Collections.synchronizedMap(new LinkedHashMap<String, reportPO>());
}
