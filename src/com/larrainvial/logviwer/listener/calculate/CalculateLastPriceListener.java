package com.larrainvial.logviwer.listener.calculate;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.listener.alert.AlertDolar;
import com.larrainvial.logviwer.listener.sendtoview.LastPriceListener;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.utils.Constants;
import org.apache.log4j.Logger;

import java.util.logging.Level;

public class CalculateLastPriceListener extends Thread {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public Algo algo;
    public String typeMarket;
    public ModelMarketData modelMarketData;

    public CalculateLastPriceListener(Algo algo, ModelMarketData modelMarketData, String typeMarket) {
        this.algo = algo;
        this.modelMarketData = modelMarketData;
        this.typeMarket = typeMarket;
    }

    @Override
    public synchronized void run() {

        try {

            synchronized (algo.lastPriceMasterListHash) {

                if (modelMarketData.symbol.equals(Constants.EMPTY)) return;

                if (!algo.lastPriceMasterListHash.containsKey(modelMarketData.symbol)) {
                    algo.lastPriceMasterListHash.put(modelMarketData.symbol, modelMarketData);

                } else {

                    if (typeMarket.equals(Constants.TypeMarket.DOLAR)) {
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

            LastPriceListener lastPriceListener = new LastPriceListener(algo, modelMarketData);
            lastPriceListener.start();

        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
        }

    }


}
