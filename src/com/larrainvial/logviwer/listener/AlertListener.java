package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.AlertEvent;
import com.larrainvial.logviwer.fxvo.Dialog;
import com.larrainvial.logviwer.utils.Helper;
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

            if (ev.messageByType.equals("9") && ev.modelRoutingData.text.equals("RMG Reject: routing failure")) {
                if(algo.isAlert()) {
                    Dialog.alert(algo.nameAlgo, " Variacion Dolar! " + ev.modelRoutingData.text + " " + ev.typeMarket);
                    Helper.printerLog(algo.nameAlgo + " Variacion Dolar! " + ev.modelRoutingData.text + " " + ev.typeMarket);
                    new Sound();
                }
            }

            if (ev.messageByType.equals("9") && ev.modelRoutingData.text.equals("RMG Reject: routing failure")) {
                if(algo.isAlert()) {
                    Dialog.alert(algo.nameAlgo, " Rejected, check log files! " + ev.modelRoutingData.text + " " + ev.typeMarket);
                    Helper.printerLog(algo.nameAlgo + " Rejected, check log files! " + ev.modelRoutingData.text + " " + ev.typeMarket);
                    new Sound();
                }
            }

            if (ev.messageByType.equals("A")) {
                if (algo.isAlert()) {
                    Dialog.alert(algo.nameAlgo, " Logon, check log files! " + " " + ev.typeMarket);
                    Helper.printerLog(algo.nameAlgo + " TestRequest, check log files! " + " " + ev.typeMarket);
                    new Sound();
                }
            }

            if (ev.messageByType.equals("1")) {
                if (algo.isAlert()) {
                    Dialog.alert(algo.nameAlgo, " TestRequest, check log files! " + " " + ev.typeMarket);
                    Helper.printerLog(algo.nameAlgo + " TestRequest, check log files! " + " " + ev.typeMarket);
                    new Sound();
                }

            }

            if (ev.messageByType.equals("5")) {
                if (algo.isAlert()) {
                    Dialog.alert(algo.nameAlgo, " Logout, check log files! " + " " + ev.typeMarket);
                    Helper.printerLog(algo.nameAlgo + " Logout, check log files! " + " " + ev.typeMarket);
                    new Sound();
                }


            }

            if (ev.messageByType.equals("3")) {
                if (algo.isAlert()) {
                    Dialog.alert(algo.nameAlgo, " Protocol, check log files! " + ev.modelRoutingData.text + " " + ev.typeMarket);
                    Helper.printerLog(algo.nameAlgo + " Protocol, check log files! " + ev.modelRoutingData.text + " " + ev.typeMarket);
                    new Sound();
                }

            }

            if (ev.messageByType.equals("8") && ev.execType.equals("8")) {
                if (algo.isAlert()) {
                    Dialog.alert(algo.nameAlgo, " Reject, check log files! " + ev.modelRoutingData.text + " " + ev.typeMarket);
                    Helper.printerLog(algo.nameAlgo + " Reject, check log files! " + ev.modelRoutingData.text + " " + ev.typeMarket);
                    new Sound();
                }

            }


        } catch (Exception e){
            Dialog.exception(e);
        }


    }



}
