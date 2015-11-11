package com.larrainvial.logviwer.listener.util;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.utils.CalculateLastPriceEvent;
import com.larrainvial.logviwer.event.sendtoview.LastPriceEvent;
import com.larrainvial.logviwer.model.Dolar;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import org.apache.log4j.Logger;
import java.util.logging.Level;


public class CalculateLastPriceListener implements Listener {

    private static Logger logger = Logger.getLogger(CalculateLastPriceListener.class.getName());
    private Algo algo;

    private final String DOLAR = "DOLAR";
    private final String VARIACION = "VARIACION DEL DOLAR";
    private final String CLOSE_CERO_DOLAR = "CLOSE DOLAR CERO";
    private final String BUY_CERO_DOLAR = "BUY DOLAR CERO";
    private final String SELL_CERO_DOLAR = "SELL DOLAR CERO";
    private final String DOLAR_PX = "DOLAR PX : ";

    public CalculateLastPriceListener(Algo algo) {
        this.algo = algo;
    }

    @Override
    public void eventOccurred(Event event) {

        try {

            CalculateLastPriceEvent ev = (CalculateLastPriceEvent)event;

            if (!this.algo.equals(ev.algo)) return;

            this.calculateLastPrice(ev.modelMarketData, ev.type_Market);

        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
        }

    }


    public void calculateLastPrice(ModelMarketData modelMarketData, String type_market) throws Exception {

        synchronized (algo.lastPriceMasterListHash) {

            if (modelMarketData.symbol.equals("")) return;

            if (!algo.lastPriceMasterListHash.containsKey(modelMarketData.symbol)) {
                algo.lastPriceMasterListHash.put(modelMarketData.symbol, modelMarketData);

            } else {

                if (type_market.equals(DOLAR)) {
                    verifyDolar(modelMarketData, type_market);
                }

                algo.lastPriceMasterListHash.get(modelMarketData.symbol).hour = modelMarketData.hour;
                algo.lastPriceMasterListHash.get(modelMarketData.symbol).messageByType = modelMarketData.messageByType;

                if (modelMarketData.buyPx != 0.0) {
                    algo.lastPriceMasterListHash.get(modelMarketData.symbol).messageByType = modelMarketData.messageByType;
                    algo.lastPriceMasterListHash.get(modelMarketData.symbol).buyPx = modelMarketData.buyPx;
                }

                if (modelMarketData.buyQty != 0.0) {
                    algo.lastPriceMasterListHash.get(modelMarketData.symbol).messageByType = modelMarketData.messageByType;
                    algo.lastPriceMasterListHash.get(modelMarketData.symbol).buyQty = modelMarketData.buyQty;
                }

                if (modelMarketData.sellPx != 0.0) {
                    algo.lastPriceMasterListHash.get(modelMarketData.symbol).messageByType = modelMarketData.messageByType;
                    algo.lastPriceMasterListHash.get(modelMarketData.symbol).sellPx = modelMarketData.sellPx;
                }

                if (modelMarketData.sellQty != 0.0) {
                    algo.lastPriceMasterListHash.get(modelMarketData.symbol).messageByType = modelMarketData.messageByType;
                    algo.lastPriceMasterListHash.get(modelMarketData.symbol).sellQty = modelMarketData.sellQty;
                }

                if (modelMarketData.closePx != 0.0) {
                    algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx = modelMarketData.closePx;
                }

                if (modelMarketData.tradeAmount != 0.0) {
                    algo.lastPriceMasterListHash.get(modelMarketData.symbol).messageByType = modelMarketData.messageByType;
                    algo.lastPriceMasterListHash.get(modelMarketData.symbol).tradeAmount = modelMarketData.tradeAmount;
                }
            }
        }

        Controller.dispatchEvent(new LastPriceEvent(algo, modelMarketData));

    }


    public void verifyDolar(ModelMarketData modelMarketData, String type_market){

        if (modelMarketData.symbol.equals(Dolar.CLP)){

            if (modelMarketData.closePx != 0.0 && algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx != 0.0) {

                Long variacion = Math.round(Math.abs(algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx - modelMarketData.closePx)* 100) / 100;

                if (variacion >= Dolar.VARIACION_CLP){

                    logger.info("-----------------");
                    String msgAlerta = algo.nameAlgo + "\n";
                    msgAlerta += modelMarketData.symbol + "\n";
                    msgAlerta += VARIACION + "\n\n";
                    msgAlerta += algo.lastPriceMasterListHash.get(modelMarketData.symbol).hour + "\t" + algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx.toString()+ "\n";
                    msgAlerta += modelMarketData.hour + "\t" + modelMarketData.closePx.toString() + "\n";
                    msgAlerta += "Variacion:\t " + variacion;

                    Notifier.INSTANCE.notifyWarning("Variacion", msgAlerta);
                    Helper.printerLog(msgAlerta);
                }
            }

            if (modelMarketData.messageByType.equals("W")){

                if (modelMarketData.closePx == 0.0){

                    logger.info("-----------------");
                    String msgAlerta = algo.nameAlgo + "\n";
                    msgAlerta += modelMarketData.hour + "\n";
                    msgAlerta += CLOSE_CERO_DOLAR + "\n";
                    msgAlerta += DOLAR_PX + "\n";
                    msgAlerta += modelMarketData.closePx.toString() + "\n";

                    Notifier.INSTANCE.notifyWarning(CLOSE_CERO_DOLAR, msgAlerta);
                    Helper.printerLog(msgAlerta);
                }

                if (modelMarketData.buyPx == 0.0){

                    String msgAlerta = algo.nameAlgo + "\n";
                    msgAlerta += modelMarketData.hour + "\n";
                    msgAlerta += BUY_CERO_DOLAR + "\n";
                    msgAlerta += DOLAR_PX + "\n";
                    msgAlerta += modelMarketData.closePx.toString() + "\n";

                    Notifier.INSTANCE.notifyWarning(BUY_CERO_DOLAR, msgAlerta);
                    Helper.printerLog(msgAlerta);
                }

                if (modelMarketData.sellPx == 0.0){

                    String msgAlerta = algo.nameAlgo + "\n";
                    msgAlerta += modelMarketData.hour + "\n";
                    msgAlerta += SELL_CERO_DOLAR + "\n";
                    msgAlerta += DOLAR_PX + "\n";
                    msgAlerta += modelMarketData.closePx.toString() + "\n";

                    Notifier.INSTANCE.notifyWarning("Variacion", msgAlerta);
                    Helper.printerLog(msgAlerta);
                }
            }
        }

        if (modelMarketData.symbol.equals(Dolar.CAD)){

            if (modelMarketData.closePx != 0.0 && algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx != 0.0) {

                Double variacion = Double.valueOf(Math.abs(algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx - modelMarketData.closePx));
                variacion = Double.valueOf(Math.round(variacion * 100) / 100);

                if (variacion >= Dolar.VARIACION_CAD){

                    String msgAlerta = algo.nameAlgo + "\n";
                    msgAlerta += modelMarketData.symbol + "\n";
                    msgAlerta += VARIACION + "\n\n";
                    msgAlerta += algo.lastPriceMasterListHash.get(modelMarketData.symbol).hour + "\t" + algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx.toString()+ "\n";
                    msgAlerta += modelMarketData.hour + "\t" + modelMarketData.closePx.toString() + "\n";
                    msgAlerta += "Variacion :\t " + variacion;

                    Notifier.INSTANCE.notifyWarning(VARIACION, msgAlerta);
                    Helper.printerLog(msgAlerta);

                }
            }



            if (modelMarketData.messageByType.equals("W") || modelMarketData.messageByType.equals("X")){

                if (modelMarketData.closePx == 0.0){

                    String msgAlerta = algo.nameAlgo + "\n";
                    msgAlerta += modelMarketData.hour + "\n";
                    msgAlerta += CLOSE_CERO_DOLAR + "\n";
                    msgAlerta += DOLAR_PX + "\n";
                    msgAlerta += modelMarketData.closePx.toString() + "\n";

                    Notifier.INSTANCE.notifyWarning(CLOSE_CERO_DOLAR, msgAlerta);
                    Notifier.INSTANCE.notifyWarning(CLOSE_CERO_DOLAR, msgAlerta);
                    Notifier.INSTANCE.notifyWarning(CLOSE_CERO_DOLAR, msgAlerta);

                    Helper.printerLog(msgAlerta);
                }
            }

        }


        if (modelMarketData.symbol.equals(Dolar.COFX)){

            if (modelMarketData.closePx != 0.0 && algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx != 0.0) {
                Long variacion = Math.round(Math.abs(algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx - modelMarketData.closePx)* 100) / 100;

                if (variacion >= Dolar.VARIACION_COFX){

                    String msgAlerta = algo.nameAlgo + "\n";
                    msgAlerta += modelMarketData.symbol + "\n";
                    msgAlerta += VARIACION + "\n\n";
                    msgAlerta += algo.lastPriceMasterListHash.get(modelMarketData.symbol).hour + "\t" + algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx.toString()+ "\n";
                    msgAlerta += modelMarketData.hour + "\t" + modelMarketData.closePx.toString() + "\n";
                    msgAlerta += "Variacion :\t " + variacion;

                    Notifier.INSTANCE.notifyWarning(VARIACION, msgAlerta);
                    Helper.printerLog(msgAlerta);

                }
            }

            if (modelMarketData.messageByType.equals("W") || modelMarketData.messageByType.equals("X")){

                if (modelMarketData.closePx == 0.0){

                    String msgAlerta = algo.nameAlgo + "\n";
                    msgAlerta += modelMarketData.hour + "\n";
                    msgAlerta += CLOSE_CERO_DOLAR + "\n";
                    msgAlerta += DOLAR_PX + "\n";
                    msgAlerta += modelMarketData.closePx.toString() + "\n";

                    Notifier.INSTANCE.notifyWarning(CLOSE_CERO_DOLAR, msgAlerta);
                    Notifier.INSTANCE.notifyWarning(CLOSE_CERO_DOLAR, msgAlerta);
                    Notifier.INSTANCE.notifyWarning(CLOSE_CERO_DOLAR, msgAlerta);

                    Helper.printerLog(msgAlerta);
                }
            }
        }


    }
}
