package com.larrainvial.logviwer.listener;


import com.larrainvial.logviwer.event.AlertEvent;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import quickfix.field.MsgType;
import quickfix.fix44.Message;

public class AlertListener implements Listener {

    @Override
    public void eventOccurred(Event event) {

        try {

            AlertEvent ev = (AlertEvent) event;

            MsgType typeOfMessage = Message.identifyType(ev.lineFromLog);


            if (typeOfMessage.getValue().equals("9")){

            }

            if(typeOfMessage.getValue().equals("A")){

            }

            if(typeOfMessage.getValue().equals("1")){

            }

            if(typeOfMessage.getValue().equals("5")){

            }

            if(typeOfMessage.getValue().equals("3")){

            }



        } catch (Exception e){
            e.printStackTrace();
        }


    }
}
