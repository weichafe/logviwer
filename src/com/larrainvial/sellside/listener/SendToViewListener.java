package com.larrainvial.sellside.listener;

import com.larrainvial.logviwer.Repository;
import com.larrainvial.sellside.event.SendToViewEvent;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import quickfix.fix44.Message;

public class SendToViewListener implements Listener {

    public Message message;

    @Override
    public void eventOccurred(Event event) {

        try {

            synchronized (this){
                SendToViewEvent ev = (SendToViewEvent) event;
                Repository.sellside.sellsideTableView.getItems().add(ev.modelRoutingData);
            }

        } catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
