package com.larrainvial.logviwer.listener.util;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.utils.AlertEvent;
import com.larrainvial.logviwer.fxvo.Dialog;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.logviwer.utils.Sound;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

import java.util.logging.Logger;

public class AlertListener implements Listener {

    public Algo algo;

    public AlertListener(Algo algo) {
        this.algo = algo;
    }

    @Override
    public void eventOccurred(Event event) {

        try {
            
            AlertEvent ev = (AlertEvent) event;

            if (!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;

            if (ev.messageByType.equals("9") && ev.modelRoutingData.text.equals("RMG Reject: routing failure")) {

                String text = "Rejected, check log files!" + "\n";
                text += ev.modelRoutingData.text + "\n";
                text += ev.typeMarket + "\n";

                Notifier.INSTANCE.notifyError(ev.algo.nameAlgo, text);
                Helper.printerLog(algo.nameAlgo + " Rejected, check log files! " + ev.modelRoutingData.text + " " + ev.typeMarket);

                if(algo.isAlert()) {
                    new Sound();
                }

            }

            if (ev.messageByType.equals("A")) {

                String text = "Rejected, check log files! " + "\n";
                text += ev.algo.nameAlgo + "\n";
                text += ev.typeMarket + "\n";

                Notifier.INSTANCE.notifyError(ev.algo.nameAlgo, text);
                Helper.printerLog(algo.nameAlgo + " TestRequest, check log files! " + " " + ev.typeMarket);

                if (algo.isAlert()) {
                    new Sound();
                }
            }

            if (ev.messageByType.equals("1")) {

                String text = "TestRequest, check log files! " + "\n";
                text += ev.typeMarket + "\n";

                Notifier.INSTANCE.notifyError(ev.algo.nameAlgo, text);
                Helper.printerLog(algo.nameAlgo + " TestRequest, check log files! " + " " + ev.typeMarket);

                if (algo.isAlert()) {
                    new Sound();
                }

            }

            if (ev.messageByType.equals("5")) {

                String text = "Logout, check log files! " + "\n";
                text += ev.typeMarket + "\n";

                Notifier.INSTANCE.notifyError(ev.algo.nameAlgo, text);
                Helper.printerLog(algo.nameAlgo + " Logout, check log files! " + " " + ev.typeMarket);

                if (algo.isAlert()) {
                    new Sound();
                }


            }

            if (ev.messageByType.equals("3")) {

                String text = "Protocol, check log files! " + "\n";
                text += ev.typeMarket + "\n";

                Notifier.INSTANCE.notifyError(ev.algo.nameAlgo, text);
                Helper.printerLog(algo.nameAlgo + " Protocol, check log files! " + ev.text + " " + ev.typeMarket);

                if (algo.isAlert()) {
                    new Sound();
                }

            }

            if (ev.messageByType.equals("8") && ev.execType.equals("8")) {

                String text = "Reject, check log files! " + "\n";
                text += ev.modelRoutingData.text + "\n";
                text += ev.typeMarket + "\n";

                Notifier.INSTANCE.notifyError(ev.algo.nameAlgo, text);
                Helper.printerLog(algo.nameAlgo + " Reject, check log files! " + ev.modelRoutingData.text + " " + ev.typeMarket);

                if (algo.isAlert()) {
                    new Sound();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
            Helper.printerLog(e.toString());
            Notifier.INSTANCE.notifyError("Error", e.toString());
        }


    }



}
