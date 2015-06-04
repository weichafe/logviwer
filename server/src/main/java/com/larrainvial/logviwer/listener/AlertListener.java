package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.AlertEvent;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import quickfix.field.MsgType;
import quickfix.fix44.Message;

public class AlertListener implements Listener {

    private  Algo algo;

    @Override
    public void eventOccurred(Event event) {

        AlertEvent ev = (AlertEvent) event;
        algo = Repository.strategy.get(ev.nameAlgo);
        Helper helper = new Helper();

        try {

            MsgType typeOfMessage = Message.identifyType(ev.lineFromLog);


            if (typeOfMessage.valueEquals("9") && algo.isAlert()){

                if(ev.modelRoutingData.text.equals("Routing Failure")) {
                    Helper.alert(algo.getNameAlgo(), "Rejected, check log files! " + ev.modelRoutingData.text );
                }

            }

            if (typeOfMessage.getValue().equals("A") && algo.isAlert()){
                Helper.alert(algo.getNameAlgo(), "Logon, check log files! " + "");
            }

            if (typeOfMessage.getValue().equals("1") && algo.isAlert()){
                Helper.alert(algo.getNameAlgo(), "TestRequest, check log files! " +"");

            }

            if(typeOfMessage.getValue().equals("5") && algo.isAlert()){
                Helper.alert(algo.getNameAlgo(), "Logout, check log files! " + "");

            }

            if(typeOfMessage.getValue().equals("3") && algo.isAlert()){
                Helper.alert(algo.getNameAlgo(), "Protocol, check log files! " + ev.modelRoutingData.text);

            }

        } catch (Exception e){
            Helper.exception(e);
        }


    }




}
