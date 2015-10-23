package com.larrainvial.sellside;

import com.larrainvial.sellside.adaptador.QuickFixAdapter;
import com.larrainvial.sellside.orders.Orders;
import com.larrainvial.sellside.utils.PropertiesFile;
import org.apache.log4j.Logger;
import quickfix.SessionID;
import quickfix.SocketAcceptor;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Repository {

    private static Logger logger = Logger.getLogger(Repository.class.getName());

    public static PropertiesFile buySide;
    public static SocketAcceptor socketAcceptor;
    public static SessionID sessionID;
    public static String socketAcceptPort;
    public static String senderCompID;
    public static String targetCompID;

    public static String date;
    public static String XPUS_NAME;
    public static String XPUS_UUID;

    public static HashMap<String, String> UUID = new HashMap<String, String>();
    public static Map<String, Orders> executionWorkOrderBuy = Collections.synchronizedMap(new LinkedHashMap<String, Orders>());
    public static Map<String, Orders> executionWorkOrderSell = Collections.synchronizedMap(new LinkedHashMap<String, Orders>());

    public static final String XLIM = "XLIM";
    public static final String XPUS = "XPUS";
    public static final String IB = "IB";
    public static final String FCSTONE = "FCSTONE";


    public static void deleteOrder(){
        executionWorkOrderBuy.clear();
        executionWorkOrderSell.clear();
        logger.info("Ordenes borradas");
    }

}
