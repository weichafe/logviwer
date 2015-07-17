package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.AlertEvent;
import com.larrainvial.logviwer.utils.Dialog;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class AlertListener implements Listener {

    public Algo algo;

    public AlertListener(Algo algo) {
        this.algo = algo;
    }


    @Override
    public void eventOccurred(Event event) {

        try {

            AlertEvent ev = (AlertEvent) event;

            if (ev.execType.equals("9") && algo.isAlert()) {
                Dialog.alert(algo.nameAlgo, "Rejected, check log files! " + ev.modelRoutingData.text );
            }

            if (ev.execType.equals("A") && algo.isAlert()) {
                Dialog.alert(algo.nameAlgo, "Logon, check log files! " + "");
            }

            if (ev.execType.equals("1") && algo.isAlert()) {
                Dialog.alert(algo.nameAlgo, "TestRequest, check log files! " +"");
            }

            if (ev.execType.equals("5") && algo.isAlert()) {
                Dialog.alert(algo.nameAlgo, "Logout, check log files! " + "");
            }

            if (ev.execType.equals("3") && algo.isAlert()) {
                Dialog.alert(algo.nameAlgo, "Protocol, check log files! " + ev.modelRoutingData.text);
            }

        } catch (Exception e){
            Dialog.exception(e);
        }


    }



}
