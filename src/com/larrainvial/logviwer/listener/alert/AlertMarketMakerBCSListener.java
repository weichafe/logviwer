package com.larrainvial.logviwer.listener.alert;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.utils.AlertEvent;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.Constants;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import javafx.application.Platform;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;

public class AlertMarketMakerBCSListener implements Listener {

    public Algo algo;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public AlertMarketMakerBCSListener(Algo algo) {
        this.algo = algo;
    }

    @Override
    public void eventOccurred(Event event) {

        try {

            AlertEvent ev = (AlertEvent) event;

            if (!ev.algo.nameAlgo.equals(Constants.MarketMakerBCS.NAME)) return;
            if (!ev.typeMarket.equals(Constants.TypeMarket.ROUTING_ADR) && !ev.typeMarket.equals(Constants.TypeMarket.ROUTING_LOCAL) ) return;
            if (Helper.validateTime(ev.hour)) return;

            //Todo: Alerta de un rechazo por cualquier motivo
            if (ev.modelRoutingData.messageByType.equals("9")) {

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(Constants.REJECTED + "\n");
                stringBuffer.append(ev.modelRoutingData.text + "\n");

                Platform.runLater(new Runnable() {
                    public void run() {
                        Notifier.INSTANCE.notifyInfo(ev.algo.nameAlgo, stringBuffer.toString());
                    }
                });

            }

            //Todo: Alerta de un trade
            if (ev.modelRoutingData.execType.equals("F")) {

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Trade MMBCS " +  ev.modelRoutingData.symbol + "\n");
                stringBuffer.append("LastPx " + ev.modelRoutingData.lastPx  + "\n");
                stringBuffer.append("LastQty " + ev.modelRoutingData.lastQty  + "\n");

                Platform.runLater(new Runnable() {
                    public void run() {
                        Notifier.INSTANCE.notifyInfo(ev.algo.nameAlgo, stringBuffer.toString());
                    }
                });
            }

            //Todo: alerta cuando el precio por la cantidad vida de un excution report no supera los 13.000.000
            if (ev.modelRoutingData.messageByType.equals("8") &&
                    ev.modelRoutingData.price * ev.modelRoutingData.leavesQty < Constants.MarketMakerBCS.PXQminBcs &&
                    !ev.modelRoutingData.execType.equals("4")){

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("MMBCS " +  ev.modelRoutingData.symbol + "\n");
                stringBuffer.append("Price "  + ev.modelRoutingData.price + "\n");
                stringBuffer.append("Qty " + ev.modelRoutingData.orderQty  + "\n");
                stringBuffer.append("LastPx " + ev.modelRoutingData.lastPx  + "\n");
                stringBuffer.append("LastQty " + ev.modelRoutingData.lastQty + "\n");

                Platform.runLater(new Runnable() {
                    public void run() {
                        Notifier.INSTANCE.notifyInfo(ev.algo.nameAlgo, stringBuffer.toString());
                        Notifier.INSTANCE.notifyInfo(ev.algo.nameAlgo, stringBuffer.toString());
                    }
                });

            }


        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }

    }




}
