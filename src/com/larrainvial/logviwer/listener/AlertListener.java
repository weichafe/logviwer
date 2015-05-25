package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.AlertEvent;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import javafx.scene.control.Alert;
import quickfix.field.MsgType;
import quickfix.fix44.Message;

public class AlertListener implements Listener {

    private  Algo algo;

    @Override
    public void eventOccurred(Event event) {

        AlertEvent ev = (AlertEvent) event;
        algo = Repository.strategy.get(ev.nameAlgo);

        try {

            MsgType typeOfMessage = Message.identifyType(ev.lineFromLog);




            if (typeOfMessage.valueEquals("9")){

                if(ev.modelRoutingData.text.equals("Routing Failure")) {
                    algo.alert(algo.getNameAlgo(), "Rejected, there was an error!" ,ev.modelRoutingData.text );

                }

            }

            if (typeOfMessage.getValue().equals("A")){
                algo.alert("","","");
                //algo.alertException.showAndWait();
                //algo.alert(algo.getNameAlgo(), "Logon, there was an error!" ,ev.modelRoutingData.text );

            }

            if (typeOfMessage.getValue().equals("1")){
                //algo.alert(algo.getNameAlgo(), "TestRequest, there was an error!" ,ev.modelRoutingData.text );

            }

            if(typeOfMessage.getValue().equals("5")){
                //algo.alert(algo.getNameAlgo(), "Logout, there was an error!" ,ev.modelRoutingData.text );

            }

            if(typeOfMessage.getValue().equals("3")){
               // algo.alert(algo.getNameAlgo(), "Protocol, there was an error!" ,ev.modelRoutingData.text );

            }



        } catch (Exception e){
            //algo.exception(e);
        }


    }
}
