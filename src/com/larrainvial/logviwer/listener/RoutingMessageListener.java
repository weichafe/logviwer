package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.event.ReadLogEvent;
import com.larrainvial.logviwer.event.RoutingMessageEvent;
import com.larrainvial.logviwer.event.SendToViewEvent;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import quickfix.field.ClOrdID;
import quickfix.fix44.Message;


public class RoutingMessageListener implements Listener {

    public ModelRoutingData modelRoutingData;

    @Override
    public void eventOccurred(Event event){

        try {

            RoutingMessageEvent ev = (RoutingMessageEvent) event;

            modelRoutingData = ev.modelRoutingData;

            this.setModelRoutingData(ev.messageFix);
            Controller.dispatchEvent(new SendToViewEvent(this, ev.nameAlgo, ev.typeMarket, modelRoutingData));


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setModelRoutingData(Message messageFix){

        try {

            if (messageFix.isSetField(ClOrdID.FIELD)) {
                modelRoutingData.setClOrdID(messageFix.getString(ClOrdID.FIELD));
                System.out.println();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}