package com.larrainvial.sellside;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.sellside.adaptador.QuickFixAdapter;
import com.larrainvial.sellside.utils.PropertiesFile;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainSellSide {

    public void sellside() {

        try {

            Repository.buySide  = new PropertiesFile("C:\\Program Files (x86)\\LarrainVial\\Logviewer\\Resources\\SellSide.properties");

            Repository.XPUS_NAME = Repository.buySide.getPropertiesString("XPUS.NAME");
            Repository.XPUS_UUID = Repository.buySide.getPropertiesString("XPUS.UUID");

            String[] XPUS_NAME = Repository.XPUS_NAME.split(",");
            String[] XPUS_UUID = Repository.XPUS_UUID.split(",");

            for (int i=0; i < XPUS_NAME.length; i++) {
                Repository.UUID.put(XPUS_NAME[i], XPUS_UUID[i]);
            }

            Repository.date = new SimpleDateFormat("yyyyMMdd").format(new Date());

            new QuickFixAdapter("C:\\Program Files (x86)\\LarrainVial\\Logviewer\\Resources\\sessionFile.ini");
            Control.initialize();

            Algo algo = new Algo(0);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
