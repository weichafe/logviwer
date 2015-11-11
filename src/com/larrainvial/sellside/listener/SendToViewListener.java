package com.larrainvial.sellside.listener;


import com.larrainvial.sellside.event.SendToViewEvent;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class SendToViewListener implements Listener {

    @Override
    public void eventOccurred(Event event) {

        try {

            SendToViewEvent ev = (SendToViewEvent) event;






        } catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
