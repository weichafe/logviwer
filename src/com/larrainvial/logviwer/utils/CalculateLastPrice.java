package com.larrainvial.logviwer.utils;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.sendtoview.LastPriceEvent;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.trading.emp.Controller;

public class CalculateLastPrice {

    private ModelMarketData modelMarketData;
    private Algo algo;
    private String type_market;
    private final String DOLAR = "DOLAR";
    private final String VARIACION = "VARIACION DEL DOLAR";
    private final String CLOSE_CERO_DOLAR = "CLOSE DOLAR CERO";
    private final String BUY_CERO_DOLAR = "BUY DOLAR CERO";
    private final String SELL_CERO_DOLAR = "SELL DOLAR CERO";
    private final String DOLAR_PX = "DOLAR PX : ";


    public CalculateLastPrice(Algo algo, ModelMarketData modelMarketData, String type_market) {

        try {

            this.type_market = type_market;
            this.modelMarketData = modelMarketData;
            this.algo = algo;
            this.calculateLastPrice();

        } catch (Exception e) {
            Dialog.exception(e);
        }
    }

    public void calculateLastPrice() throws Exception {

        synchronized (algo.lastPriceMasterListHash) {

            if (modelMarketData.symbol.equals("")) return;

            if (!algo.lastPriceMasterListHash.containsKey(modelMarketData.symbol)) {
                algo.lastPriceMasterListHash.put(modelMarketData.symbol, modelMarketData);

            } else {

                if (type_market.equals(DOLAR)) {
                    verifyDolar();
                }

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


    public void verifyDolar(){

        if (modelMarketData.symbol.equals(Dolar.CLP)){

            if (modelMarketData.closePx != 0.0 && algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx != 0.0) {
                if (Math.round(Math.abs(algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx - modelMarketData.closePx)* 100) /100 >= Dolar.VARIACION_CLP){
                    Dialog.alert(algo.nameAlgo + " " + VARIACION,
                            algo.lastPriceMasterListHash.get(modelMarketData.symbol).hour + " " + algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx.toString()
                                    + " = > " + modelMarketData.hour + " " + modelMarketData.closePx.toString());
                }
            }

            if (modelMarketData.messageByType.equals("W")){

                if (modelMarketData.closePx == 0.0){
                    Dialog.alert(algo.nameAlgo + " " + CLOSE_CERO_DOLAR, DOLAR_PX + " = > " + modelMarketData.closePx.toString());
                }

                if (modelMarketData.buyPx == 0.0){
                    Dialog.alert(algo.nameAlgo + " " + BUY_CERO_DOLAR, DOLAR_PX + " = > " + modelMarketData.buyPx.toString());
                }

                if (modelMarketData.sellPx == 0.0){
                    Dialog.alert(algo.nameAlgo + " " + SELL_CERO_DOLAR, DOLAR_PX + " = > " + modelMarketData.buyPx.toString());
                }
            }
        }

        if (modelMarketData.symbol.equals(Dolar.CAD)){

            if (modelMarketData.closePx != 0.0 && algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx != 0.0) {
                if (Math.round(Math.abs(algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx - modelMarketData.closePx)* 100) /100 >= Dolar.VARIACION_CAD){
                    Dialog.alert(algo.nameAlgo + " " + VARIACION,
                            algo.lastPriceMasterListHash.get(modelMarketData.symbol).hour + " " + algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx.toString()
                                    + " = > " + modelMarketData.hour + " " + modelMarketData.closePx.toString());
                }
            }

            if (modelMarketData.messageByType.equals("W") || modelMarketData.messageByType.equals("X")){

                if (modelMarketData.closePx == 0.0){
                    Dialog.alert(algo.nameAlgo + " " + BUY_CERO_DOLAR, DOLAR_PX + " = > " + modelMarketData.closePx.toString());
                }
            }

        }


        if (modelMarketData.symbol.equals(Dolar.COFX)){

            if (modelMarketData.closePx != 0.0 && algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx != 0.0) {
                if (Math.round(Math.abs(algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx - modelMarketData.closePx)* 100) /100 >= Dolar.VARIACION_COFX){
                    Dialog.alert(algo.nameAlgo + " " + VARIACION,
                            algo.lastPriceMasterListHash.get(modelMarketData.symbol).hour + " " + algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx.toString()
                                    + " = > " + modelMarketData.hour + " " + modelMarketData.closePx.toString());
                }
            }

            if (modelMarketData.messageByType.equals("W") || modelMarketData.messageByType.equals("X")){
                if (modelMarketData.closePx == 0.0){
                    Dialog.alert(algo.nameAlgo + " " + BUY_CERO_DOLAR, DOLAR_PX + " = > " + modelMarketData.closePx.toString());
                }
            }
        }


    }
}
