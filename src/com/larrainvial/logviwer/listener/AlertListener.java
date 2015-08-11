package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.AlertEvent;
import com.larrainvial.logviwer.utils.Dialog;
import com.larrainvial.logviwer.utils.Sound;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

import java.util.logging.Logger;

public class AlertListener implements Listener {

    public Algo algo;
    private static Logger LOGGER = Logger.getLogger(AlertListener.class.getName());

    public AlertListener(Algo algo) {
        this.algo = algo;
    }


    @Override
    public void eventOccurred(Event event) {

        try {

            AlertEvent ev = (AlertEvent) event;

            if (!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;

            if (ev.execType.equals("9") && ev.modelRoutingData.text.equals("RMG Reject: routing failure")) {
                if(algo.isAlert()) {
                    Dialog.alert(algo.nameAlgo, " Rejected, check log files! " + ev.modelRoutingData.text + " " + ev.typeMarket);
                    LOGGER.info(algo.nameAlgo + " Rejected, check log files! " + ev.modelRoutingData.text + " " + ev.typeMarket);
                    new Sound();
                }
            }

            if (ev.execType.equals("A")) {
                if (algo.isAlert()) {
                    Dialog.alert(algo.nameAlgo, " Logon, check log files! " + " " + ev.typeMarket);
                    LOGGER.info(algo.nameAlgo + " Logon, check log files!" + " " + ev.typeMarket);
                    new Sound();
                }
            }

            if (ev.execType.equals("1")) {
                if (algo.isAlert()) {
                    Dialog.alert(algo.nameAlgo, " TestRequest, check log files! " + " " + ev.typeMarket);
                    LOGGER.info(algo.nameAlgo + " TestRequest, check log files! " + " " + ev.typeMarket);
                    new Sound();
                }

            }

            if (ev.execType.equals("5")) {
                if (algo.isAlert()) {
                    Dialog.alert(algo.nameAlgo, " Logout, check log files! " + " " + ev.typeMarket);
                    LOGGER.info(algo.nameAlgo + " Logout, check log files! " + " " + ev.typeMarket);
                    new Sound();
                }


            }

            if (ev.execType.equals("3")) {
                if (algo.isAlert()) {
                    Dialog.alert(algo.nameAlgo, " Protocol, check log files! " + ev.modelRoutingData.text + " " + ev.typeMarket);
                    LOGGER.info(algo.nameAlgo + " Protocol, check log files! " + ev.modelRoutingData.text + " " + ev.typeMarket);
                    new Sound();
                }

            }

        } catch (Exception e){
            Dialog.exception(e);
        }


    }



}
