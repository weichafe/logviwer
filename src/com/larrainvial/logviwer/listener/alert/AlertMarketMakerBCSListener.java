package com.larrainvial.logviwer.listener.alert;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.Constants;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import javafx.application.Platform;
import org.apache.log4j.Logger;
import java.util.logging.Level;

public class AlertMarketMakerBCSListener extends Thread {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public ModelRoutingData modelRoutingData;
    public String execType;
    public String messageByType;
    public String typeMarket;
    public Algo algo;
    public String text;
    public String hour;

    public AlertMarketMakerBCSListener(Algo algo, ModelRoutingData modelRoutingData, String typeMarket) {
        this.algo = algo;
        this.modelRoutingData = modelRoutingData;
        this.execType = modelRoutingData.execType;
        this.messageByType = modelRoutingData.messageByType;
        this.typeMarket = typeMarket;
        this.text = modelRoutingData.text;
        this.hour = modelRoutingData.hour;
    }

    @Override
    public void run() {

        try {

            if (!algo.nameAlgo.equals(Constants.MarketMakerBCS.NAME)) return;
            if (!typeMarket.equals(Constants.TypeMarket.ROUTING_ADR) && !typeMarket.equals(Constants.TypeMarket.ROUTING_LOCAL) ) return;
            if (Helper.validateTime(hour)) return;

            //Todo: Alerta de un rechazo por cualquier motivo
            if (modelRoutingData.messageByType.equals("9")) {

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(Constants.REJECTED + "\n");
                stringBuffer.append(modelRoutingData.text + "\n");

                Platform.runLater(new Runnable() {
                    public void run() {
                        Notifier.INSTANCE.notifyInfo(algo.nameAlgo, stringBuffer.toString());
                    }
                });
            }

            //Todo: Alerta de un trade
            if (modelRoutingData.execType.equals("F")) {

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Trade MMBCS " +  modelRoutingData.symbol + "\n");
                stringBuffer.append("LastPx " + modelRoutingData.lastPx  + "\n");
                stringBuffer.append("LastQty " + modelRoutingData.lastQty  + "\n");

                Platform.runLater(new Runnable() {
                    public void run() {
                        Notifier.INSTANCE.notifyInfo(algo.nameAlgo, stringBuffer.toString());
                    }
                });
            }

            //Todo: alerta cuando el precio por la cantidad vida de un excution report no supera los 13.000.000
            if (modelRoutingData.messageByType.equals("8") &&
                    modelRoutingData.price * modelRoutingData.leavesQty < Constants.MarketMakerBCS.PXQminBcs &&
                    !modelRoutingData.execType.equals("4")){

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("MMBCS " +  modelRoutingData.symbol + "\n");
                stringBuffer.append("Price "  + modelRoutingData.price + "\n");
                stringBuffer.append("Qty " + modelRoutingData.orderQty  + "\n");
                stringBuffer.append("LastPx " + modelRoutingData.lastPx  + "\n");
                stringBuffer.append("LastQty " + modelRoutingData.lastQty + "\n");

                Platform.runLater(new Runnable() {
                    public void run() {
                        Notifier.INSTANCE.notifyInfo(algo.nameAlgo, stringBuffer.toString());
                    }
                });

            }


        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }

    }




}
