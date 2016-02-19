package com.larrainvial.logviwer.listener.alert;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.utils.AlertEvent;
import com.larrainvial.logviwer.utils.Constants;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import javafx.application.Platform;
import org.apache.log4j.Logger;
import quickfix.field.ExecType;
import quickfix.fix44.OrderCancelReject;
import quickfix.fix44.ExecutionReport;
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
            if (!ev.typeMarket.equals(Constants.TypeMarket.ROUTING_ADR) && !ev.typeMarket.equals(Constants.TypeMarket.ROUTING_LOCAL)) return;
            if (Helper.validateTime(ev.hour)) return;

            //Todo: Alerta de un rechazo por cualquier motivo
            if (ev.modelRoutingData.messageByType.equals(OrderCancelReject.MSGTYPE)) {

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(Constants.REJECTED);
                stringBuffer.append(Constants.LN2);
                stringBuffer.append(ev.modelRoutingData.text);
                stringBuffer.append(Constants.LN);

                Platform.runLater(new Runnable() {
                    public void run() {
                        Notifier.INSTANCE.notifyWarning(ev.algo.nameAlgo, stringBuffer.toString());
                    }
                });

            }

            //Todo: Alerta de un trade
            if (ev.modelRoutingData.execType.equals(ExecType.TRADE)) {

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("TRADE");
                stringBuffer.append(Constants.LN2);

                stringBuffer.append(ev.modelRoutingData.symbol);
                stringBuffer.append(Constants.LN);

                stringBuffer.append("LastPx ");
                stringBuffer.append(Constants.LN);
                stringBuffer.append(ev.modelRoutingData.lastPx);
                stringBuffer.append(Constants.LN);

                stringBuffer.append("LastQty ");
                stringBuffer.append(ev.modelRoutingData.lastQty);
                stringBuffer.append(Constants.LN);

                Platform.runLater(new Runnable() {
                    public void run() {
                        Notifier.INSTANCE.notifyInfo(ev.algo.nameAlgo, stringBuffer.toString());
                    }
                });
            }

            //Todo: alerta cuando el precio por la cantidad vida de un excution report no supera los 13.000.000
            if (ev.modelRoutingData.messageByType.equals(ExecutionReport.MSGTYPE) &&
                    ev.modelRoutingData.price * ev.modelRoutingData.leavesQty < Constants.MarketMakerBCS.PXQminBcs &&
                    !ev.modelRoutingData.execType.equals(ExecType.CANCELED)) {

                StringBuffer stringBuffer = new StringBuffer();

                stringBuffer.append(ev.modelRoutingData.symbol);
                stringBuffer.append(Constants.LN);

                stringBuffer.append("Price ");
                stringBuffer.append(ev.modelRoutingData.price);
                stringBuffer.append(Constants.LN);

                stringBuffer.append("Qty ");
                stringBuffer.append(ev.modelRoutingData.orderQty);
                stringBuffer.append(Constants.LN);

                stringBuffer.append("LastPx ");
                stringBuffer.append(ev.modelRoutingData.lastPx);
                stringBuffer.append(Constants.LN);

                stringBuffer.append("LastQty ");
                stringBuffer.append(ev.modelRoutingData.lastQty);
                stringBuffer.append(Constants.LN);

                Platform.runLater(new Runnable() {
                    public void run() {
                        Notifier.INSTANCE.notifyWarning(ev.algo.nameAlgo, stringBuffer.toString());
                    }
                });

            }


        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }

    }




}
