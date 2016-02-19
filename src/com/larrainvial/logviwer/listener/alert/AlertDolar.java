package com.larrainvial.logviwer.listener.alert;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.model.ModelDolar;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.utils.Constants;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import org.apache.log4j.Logger;

import java.util.logging.Level;

public class AlertDolar {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public AlertDolar(ModelMarketData modelMarketData, Algo algo) {

        try {

            if (modelMarketData.symbol.equals(ModelDolar.CLP)) {

                if (modelMarketData.closePx != 0.0 && algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx != 0.0) {

                    Long variacion = Math.round(Math.abs(algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx - modelMarketData.closePx) * 100) / 100;

                    if (variacion >= ModelDolar.VARIACION_CLP) {

                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append(algo.nameAlgo);
                        stringBuffer.append(Constants.LN);
                        stringBuffer.append(modelMarketData.symbol);
                        stringBuffer.append(Constants.LastPrice.VARIACION);
                        stringBuffer.append(Constants.LN2);
                        stringBuffer.append(algo.lastPriceMasterListHash.get(modelMarketData.symbol).hour);
                        stringBuffer.append(Constants.TAB);
                        stringBuffer.append(algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx.toString());
                        stringBuffer.append(Constants.LN);
                        stringBuffer.append(modelMarketData.hour);
                        stringBuffer.append(Constants.TAB);
                        stringBuffer.append(modelMarketData.closePx.toString());
                        stringBuffer.append(Constants.LN);
                        stringBuffer.append("Variacion:");
                        stringBuffer.append(Constants.TAB);
                        stringBuffer.append(variacion);

                        Notifier.INSTANCE.notifyWarning("Variacion", stringBuffer.toString());
                        Helper.printerLog(stringBuffer.toString());
                    }
                }

                if (modelMarketData.messageByType.equals("W")) {

                    if (modelMarketData.closePx == 0.0) {

                        StringBuffer stringBuffer = new StringBuffer();

                        stringBuffer.append(algo.nameAlgo);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(modelMarketData.hour);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(Constants.LastPrice.CLOSE_CERO_DOLAR);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(modelMarketData.closePx.toString());
                        stringBuffer.append(Constants.LN);

                        Notifier.INSTANCE.notifyWarning(Constants.LastPrice.CLOSE_CERO_DOLAR, stringBuffer.toString());
                        Helper.printerLog(stringBuffer.toString());
                    }

                    if (modelMarketData.buyPx == 0.0) {

                        StringBuffer stringBuffer = new StringBuffer();

                        stringBuffer.append(algo.nameAlgo);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(Constants.LastPrice.BUY_CERO_DOLAR );
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(Constants.LastPrice.DOLAR_PX);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(modelMarketData.closePx.toString());
                        stringBuffer.append(Constants.LN);

                        Notifier.INSTANCE.notifyWarning(Constants.LastPrice.BUY_CERO_DOLAR, stringBuffer.toString());
                        Helper.printerLog(stringBuffer.toString());
                    }

                    if (modelMarketData.sellPx == 0.0) {

                        StringBuffer stringBuffer = new StringBuffer();

                        stringBuffer.append(algo.nameAlgo);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(modelMarketData.hour);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(Constants.LastPrice.SELL_CERO_DOLAR);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(Constants.LastPrice.DOLAR_PX);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(modelMarketData.closePx.toString());
                        stringBuffer.append(Constants.LN);


                        Notifier.INSTANCE.notifyWarning("Variacion", stringBuffer.toString());
                        Helper.printerLog(stringBuffer.toString());
                    }
                }
            }

            if (modelMarketData.symbol.equals(ModelDolar.CAD)) {

                if (modelMarketData.closePx != 0.0 && algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx != 0.0) {

                    Double variacion = Double.valueOf(Math.abs(algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx - modelMarketData.closePx));
                    variacion = Double.valueOf(Math.round(variacion * 100) / 100);

                    if (variacion >= ModelDolar.VARIACION_CAD) {

                        StringBuffer stringBuffer = new StringBuffer();

                        stringBuffer.append(algo.nameAlgo);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(modelMarketData.symbol);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(Constants.LastPrice.VARIACION);
                        stringBuffer.append(Constants.LN2);

                        stringBuffer.append(algo.lastPriceMasterListHash.get(modelMarketData.symbol).hour);
                        stringBuffer.append(Constants.TAB);
                        stringBuffer.append(algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx.toString());
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(modelMarketData.hour);
                        stringBuffer.append(Constants.TAB);
                        stringBuffer.append(modelMarketData.closePx.toString());
                        stringBuffer.append(Constants.LN);

                        Notifier.INSTANCE.notifyWarning(Constants.LastPrice.VARIACION, stringBuffer.toString());
                        Helper.printerLog(stringBuffer.toString());

                    }
                }


                if (modelMarketData.messageByType.equals("W") || modelMarketData.messageByType.equals("X")) {

                    if (modelMarketData.closePx == 0.0) {

                        StringBuffer stringBuffer = new StringBuffer();

                        stringBuffer.append(algo.nameAlgo);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(modelMarketData.hour);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(Constants.LastPrice.CLOSE_CERO_DOLAR);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(Constants.LastPrice.DOLAR_PX);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(modelMarketData.closePx.toString());
                        stringBuffer.append(Constants.LN);

                        Notifier.INSTANCE.notifyWarning(Constants.LastPrice.CLOSE_CERO_DOLAR, stringBuffer.toString());
                        Helper.printerLog(stringBuffer.toString());
                    }
                }

            }

            if (modelMarketData.symbol.equals(ModelDolar.COFX)) {

                if (modelMarketData.closePx != 0.0 && algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx != 0.0) {
                    Long variacion = Math.round(Math.abs(algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx - modelMarketData.closePx) * 100) / 100;

                    if (variacion >= ModelDolar.VARIACION_COFX) {

                        StringBuffer stringBuffer = new StringBuffer();

                        stringBuffer.append(algo.nameAlgo);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(modelMarketData.symbol);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(Constants.LastPrice.VARIACION);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(algo.lastPriceMasterListHash.get(modelMarketData.symbol).hour);
                        stringBuffer.append(Constants.TAB);
                        stringBuffer.append(algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx.toString());
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append("Variacion :");
                        stringBuffer.append(Constants.TAB);
                        stringBuffer.append(variacion);


                        Notifier.INSTANCE.notifyWarning(Constants.LastPrice.VARIACION, stringBuffer.toString());
                        Helper.printerLog(stringBuffer.toString());
                    }
                }

                if (modelMarketData.messageByType.equals("W") || modelMarketData.messageByType.equals("X")) {

                    if (modelMarketData.closePx == 0.0 || modelMarketData.closePx == 0) {

                        StringBuffer stringBuffer = new StringBuffer();

                        stringBuffer.append(algo.nameAlgo);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(modelMarketData.hour);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(Constants.LastPrice.CLOSE_CERO_DOLAR);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(Constants.LastPrice.DOLAR_PX);
                        stringBuffer.append(Constants.LN);

                        stringBuffer.append(modelMarketData.closePx.toString());
                        stringBuffer.append(Constants.LN);


                        Notifier.INSTANCE.notifyWarning(Constants.LastPrice.CLOSE_CERO_DOLAR, stringBuffer.toString());
                        Helper.printerLog(stringBuffer.toString());
                    }

                }
            }

        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }

    }


}
