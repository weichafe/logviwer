package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.event.ReadLogEvent;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;


public class RoutingMessageListener implements Listener {

    @Override
    public void eventOccurred(Event event){

        try {

            ReadLogEvent ev = (ReadLogEvent) event;
            //this.readlogDolar(ev.namefile, ev.nameAlgo , ev.typeMarket);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
