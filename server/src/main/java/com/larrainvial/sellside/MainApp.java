package com.larrainvial.sellside;

import com.larrainvial.sellside.adaptador.QuickFixAdapter;
import com.larrainvial.sellside.utils.PropertiesFile;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainApp {

    public static void sellside() {

        try {

            Repository.buySide  = new PropertiesFile("src/resources/SellSide.properties");

            Repository.qFixFile = Repository.buySide.getPropertiesString("qFixFile");

            Repository.XPUS_NAME = Repository.buySide.getPropertiesString("XPUS.NAME");
            Repository.XPUS_UUID = Repository.buySide.getPropertiesString("XPUS.UUID");

            String[] XPUS_NAME = Repository.XPUS_NAME.split(",");
            String[] XPUS_UUID = Repository.XPUS_UUID.split(",");

            for (int i=0; i < XPUS_NAME.length; i++) {
                Repository.UUID.put(XPUS_NAME[i], XPUS_UUID[i]);
            }

            SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
            Repository.date = formato.format(new Date());

            /*
            Repository.executionWorkOrderClOrdIDFile = new FileRepository(Repository.date + "executionWorkOrder", pathFiles);

            if (Repository.executionWorkOrderClOrdIDFile.existFile()){
                Repository.executionWorkOrder = (HashMap<String, Orders>) Repository.executionWorkOrderClOrdIDFile.readFile();
            }
            */

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
