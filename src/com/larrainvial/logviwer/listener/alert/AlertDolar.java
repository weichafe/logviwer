package com.larrainvial.logviwer.listener.alert;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.model.ModelDolar;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.utils.Constants;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import org.apache.log4j.Logger;

public class AlertDolar {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public AlertDolar(ModelMarketData modelMarketData, Algo algo){

        if (modelMarketData.symbol.equals(ModelDolar.CLP)){

            if (modelMarketData.closePx != 0.0 && algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx != 0.0) {

                Long variacion = Math.round(Math.abs(algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx - modelMarketData.closePx)* 100) / 100;

                if (variacion >= ModelDolar.VARIACION_CLP){

                    logger.info("-----------------");
                    String msgAlerta = algo.nameAlgo + "\n";
                    msgAlerta += modelMarketData.symbol + "\n";
                    msgAlerta += Constants.LastPrice.VARIACION + "\n\n";
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
                    msgAlerta += Constants.LastPrice.CLOSE_CERO_DOLAR + "\n";
                    msgAlerta += Constants.LastPrice.DOLAR_PX + "\n";
                    msgAlerta += modelMarketData.closePx.toString() + "\n";

                    Notifier.INSTANCE.notifyWarning(Constants.LastPrice.CLOSE_CERO_DOLAR, msgAlerta);
                    Helper.printerLog(msgAlerta);
                }

                if (modelMarketData.buyPx == 0.0){

                    String msgAlerta = algo.nameAlgo + "\n";
                    msgAlerta += modelMarketData.hour + "\n";
                    msgAlerta += Constants.LastPrice.BUY_CERO_DOLAR + "\n";
                    msgAlerta += Constants.LastPrice.DOLAR_PX + "\n";
                    msgAlerta += modelMarketData.closePx.toString() + "\n";

                    Notifier.INSTANCE.notifyWarning(Constants.LastPrice.BUY_CERO_DOLAR, msgAlerta);
                    Helper.printerLog(msgAlerta);
                }

                if (modelMarketData.sellPx == 0.0){

                    String msgAlerta = algo.nameAlgo + "\n";
                    msgAlerta += modelMarketData.hour + "\n";
                    msgAlerta += Constants.LastPrice.SELL_CERO_DOLAR + "\n";
                    msgAlerta += Constants.LastPrice.DOLAR_PX + "\n";
                    msgAlerta += modelMarketData.closePx.toString() + "\n";

                    Notifier.INSTANCE.notifyWarning("Variacion", msgAlerta);
                    Helper.printerLog(msgAlerta);
                }
            }
        }

        if (modelMarketData.symbol.equals(ModelDolar.CAD)){

            if (modelMarketData.closePx != 0.0 && algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx != 0.0) {

                Double variacion = Double.valueOf(Math.abs(algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx - modelMarketData.closePx));
                variacion = Double.valueOf(Math.round(variacion * 100) / 100);

                if (variacion >= ModelDolar.VARIACION_CAD){

                    String msgAlerta = algo.nameAlgo + "\n";
                    msgAlerta += modelMarketData.symbol + "\n";
                    msgAlerta += Constants.LastPrice.VARIACION + "\n\n";
                    msgAlerta += algo.lastPriceMasterListHash.get(modelMarketData.symbol).hour + "\t" + algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx.toString()+ "\n";
                    msgAlerta += modelMarketData.hour + "\t" + modelMarketData.closePx.toString() + "\n";
                    msgAlerta += "Variacion :\t " + variacion;

                    Notifier.INSTANCE.notifyWarning(Constants.LastPrice.VARIACION, msgAlerta);
                    Helper.printerLog(msgAlerta);

                }
            }


            if (modelMarketData.messageByType.equals("W") || modelMarketData.messageByType.equals("X")){

                if (modelMarketData.closePx == 0.0){

                    String msgAlerta = algo.nameAlgo + "\n";
                    msgAlerta += modelMarketData.hour + "\n";
                    msgAlerta += Constants.LastPrice.CLOSE_CERO_DOLAR + "\n";
                    msgAlerta += Constants.LastPrice.DOLAR_PX + "\n";
                    msgAlerta += modelMarketData.closePx.toString() + "\n";

                    Notifier.INSTANCE.notifyWarning(Constants.LastPrice.CLOSE_CERO_DOLAR, msgAlerta);
                    Notifier.INSTANCE.notifyWarning(Constants.LastPrice.CLOSE_CERO_DOLAR, msgAlerta);
                    Notifier.INSTANCE.notifyWarning(Constants.LastPrice.CLOSE_CERO_DOLAR, msgAlerta);

                    Helper.printerLog(msgAlerta);
                }
            }

        }

        if (modelMarketData.symbol.equals(ModelDolar.COFX)){

            if (modelMarketData.closePx != 0.0 && algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx != 0.0) {
                Long variacion = Math.round(Math.abs(algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx - modelMarketData.closePx)* 100) / 100;

                if (variacion >= ModelDolar.VARIACION_COFX){

                    String msgAlerta = algo.nameAlgo + "\n";
                    msgAlerta += modelMarketData.symbol + "\n";
                    msgAlerta += Constants.LastPrice.VARIACION + "\n\n";
                    msgAlerta += algo.lastPriceMasterListHash.get(modelMarketData.symbol).hour + "\t" + algo.lastPriceMasterListHash.get(modelMarketData.symbol).closePx.toString()+ "\n";
                    msgAlerta += modelMarketData.hour + "\t" + modelMarketData.closePx.toString() + "\n";
                    msgAlerta += "Variacion :\t " + variacion;

                    Notifier.INSTANCE.notifyWarning(Constants.LastPrice.VARIACION, msgAlerta);
                    Helper.printerLog(msgAlerta);

                }
            }

            if (modelMarketData.messageByType.equals("W") || modelMarketData.messageByType.equals("X")){

                if (modelMarketData.closePx == 0.0 || modelMarketData.closePx == 0){

                    String msgAlerta = algo.nameAlgo + "\n";
                    msgAlerta += modelMarketData.hour + "\n";
                    msgAlerta += Constants.LastPrice.CLOSE_CERO_DOLAR + "\n";
                    msgAlerta += Constants.LastPrice.DOLAR_PX + "\n";
                    msgAlerta += modelMarketData.closePx.toString() + "\n";

                    Notifier.INSTANCE.notifyWarning(Constants.LastPrice.CLOSE_CERO_DOLAR, msgAlerta);
                    Notifier.INSTANCE.notifyWarning(Constants.LastPrice.CLOSE_CERO_DOLAR, msgAlerta);
                    Notifier.INSTANCE.notifyWarning(Constants.LastPrice.CLOSE_CERO_DOLAR, msgAlerta);

                    Helper.printerLog(msgAlerta);
                }
            }
        }


    }

}
