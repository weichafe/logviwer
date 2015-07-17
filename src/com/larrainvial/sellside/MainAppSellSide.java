package com.larrainvial.sellside;

import com.larrainvial.sellside.adaptador.QuickFixAdapter;
import com.larrainvial.sellside.utils.PropertiesFile;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainAppSellSide {

    public void sellside() {

        try {

            Repository.buySide  = new PropertiesFile("C:/workspaceGit/logviwer/src/resources/SellSide.properties");

            Repository.qFixFile = Repository.buySide.getPropertiesString("qFixFile");
            Repository.XPUS_NAME = Repository.buySide.getPropertiesString("XPUS.NAME");
            Repository.XPUS_UUID = Repository.buySide.getPropertiesString("XPUS.UUID");

            String[] XPUS_NAME = Repository.XPUS_NAME.split(",");
            String[] XPUS_UUID = Repository.XPUS_UUID.split(",");

            for (int i=0; i < XPUS_NAME.length; i++) {
                Repository.UUID.put(XPUS_NAME[i], XPUS_UUID[i]);
            }

            Repository.date =  new SimpleDateFormat("yyyyMMdd").format(new Date());

            StartFixApp();
            Control.initialize();


        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private static void StartFixApp() throws Exception {
        Repository.quickFixAdapter = new QuickFixAdapter(Repository.qFixFile);
    }

}
