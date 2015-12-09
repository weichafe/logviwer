package com.larrainvial.sellside;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.sellside.adaptador.QuickFixAdapter;
import com.larrainvial.sellside.utils.PropertiesFile;
import org.apache.log4j.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

public class MainSellSide {

    private static Logger logger = Logger.getLogger(MainSellSide.class.getName());

    public void start() {

        try {

            Repository.buySide  = new PropertiesFile(com.larrainvial.logviwer.Repository.locationPath + "SellSide.properties");

            Repository.XPUS_NAME = Repository.buySide.getPropertiesString("XPUS.NAME");
            Repository.XPUS_UUID = Repository.buySide.getPropertiesString("XPUS.UUID");

            String[] XPUS_NAME = Repository.XPUS_NAME.split(",");
            String[] XPUS_UUID = Repository.XPUS_UUID.split(",");

            for (int i=0; i < XPUS_NAME.length; i++) {
                Repository.UUID.put(XPUS_NAME[i], XPUS_UUID[i]);
            }

            Repository.date = new SimpleDateFormat("yyyyMMdd").format(new Date());
            Control.initialize();

            Repository.sellside =  new Algo();

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(Level.SEVERE, ex);
        }
    }



}
