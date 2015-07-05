package com.larrainvial.logviwer.utils;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.sendtoview.LastPriceEvent;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.trading.emp.Controller;

public class CalculateLastPrice {

    private ModelMarketData modelMarketData;
    private Algo algo;

    public CalculateLastPrice(Algo algo, ModelMarketData modelMarketData) {

        try {

            this.modelMarketData = modelMarketData;
            this.algo = algo;
            this.calculateLastPrice();

        } catch (Exception e) {
            Helper.exception(e);
        }
    }

    public void calculateLastPrice() throws Exception {


        synchronized (algo.lastPriceMasterListHash) {

            if (modelMarketData.symbol.equals("")) return;

            if (!algo.lastPriceMasterListHash.containsKey(modelMarketData.symbol)) {

                algo.lastPriceMasterListHash.put(modelMarketData.symbol, modelMarketData);

            } else {

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

                if (modelMarketData.composite != 0.0) {
                    algo.lastPriceMasterListHash.get(modelMarketData.symbol).messageByType = modelMarketData.messageByType;
                    algo.lastPriceMasterListHash.get(modelMarketData.symbol).composite = modelMarketData.composite;
                }

            }
        }

        Controller.dispatchEvent(new LastPriceEvent(algo, modelMarketData));

    }
}
