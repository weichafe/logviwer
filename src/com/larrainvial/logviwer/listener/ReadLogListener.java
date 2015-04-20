package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.event.ReadLogEvent;
import com.larrainvial.logviwer.event.StringToFixMessageEvent;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import java.io.BufferedReader;
import java.io.FileReader;


public class ReadLogListener implements Listener {

    @Override
    public void eventOccurred(Event event){

        try {

            ReadLogEvent ev = (ReadLogEvent) event;
            this.readlogDolar(ev.namefile, ev.nameAlgo , ev.typeMarket);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void readlogDolar(String namefile, String nameAlgo , String typeMarket) {

        try {

            String lineFromLog;
            FileReader f = new FileReader(namefile);
            BufferedReader b = new BufferedReader(f);

            while ((lineFromLog = b.readLine()) != null){
                Controller.dispatchEvent(new StringToFixMessageEvent(this, lineFromLog, nameAlgo, typeMarket));
            }

        }catch (Exception e) {
            e.printStackTrace();
        }


    }
}
