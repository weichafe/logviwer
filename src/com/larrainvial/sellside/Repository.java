package com.larrainvial.sellside;

import com.larrainvial.logviwer.utils.FileRepository;
import com.larrainvial.sellside.adaptador.QuickFixAdapter;
import com.larrainvial.sellside.orders.Orders;
import com.larrainvial.sellside.utils.PropertiesFile;


import java.util.HashMap;
import java.util.Map;

public class Repository {

    public static QuickFixAdapter quickFixAdapter;
    public static PropertiesFile buySide;

    public static String date;
    public static String XPUS_NAME;
    public static String XPUS_UUID;
    public static String qFixFile;


    public static HashMap<String, String> UUID = new HashMap<String, String>();

    public static Map<String, Orders> executionWorkOrderBuy = new HashMap<String, Orders>();
    public static Map<String, Orders> executionWorkOrderSell = new HashMap<String, Orders>();

    public static FileRepository executionWorkOrderClOrdIDFile;

    public static final String XLIM = "XLIM";
    public static final String XPUS = "XPUS";
    public static final String IB = "IB";
    public static final String FCSTONE = "FCSTONE";


}
