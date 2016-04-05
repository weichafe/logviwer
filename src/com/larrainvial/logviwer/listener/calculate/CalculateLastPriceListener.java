package com.larrainvial.logviwer.listener.calculate;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.utils.CalculateLastPriceEvent;
import com.larrainvial.logviwer.event.sendtoview.LastPriceEvent;
import com.larrainvial.logviwer.listener.alert.AlertDolar;
import com.larrainvial.logviwer.model.ModelDolar;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.utils.Constants;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import org.apache.log4j.Logger;
import java.util.logging.Level;

public class CalculateLastPriceListener implements Listener {

    private Algo algo;
    private Logger logger = Logger.getLogger(this.getClass().getName());

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
            logger.error(algo.nameAlgo);
        }

    }


    public void calculateLastPrice(ModelMarketData modelMarketData, String type_market) throws Exception {

        synchronized (algo.lastPriceMasterListHash) {

            if (modelMarketData.symbol.equals(Constants.EMPTY)) return;

            if (!algo.lastPriceMasterListHash.containsKey(modelMarketData.symbol)) {
                algo.lastPriceMasterListHash.put(modelMarketData.symbol, modelMarketData);

            } else {

                if (type_market.equals(Constants.TypeMarket.DOLAR)) {
                    new AlertDolar(modelMarketData, algo);
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


}
