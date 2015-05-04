package com.larrainvial.logviwer.listener;


import com.larrainvial.logviwer.event.ReadLogEvent;
import com.larrainvial.logviwer.event.StringToFixMessageEvent;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class ReadLogListener implements Listener {


    @Override
    public void eventOccurred(Event event){

        try {

            ReadLogEvent ev = (ReadLogEvent) event;

            String lineFromLog;

            System.out.println("read LOG " + ev.nameAlgo + " " + ev.typeMarket);

            while ((lineFromLog = ev.bufferedReader.readLine()) != null) {
                Controller.dispatchEvent(new StringToFixMessageEvent(this, lineFromLog, ev.nameAlgo, ev.typeMarket));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
