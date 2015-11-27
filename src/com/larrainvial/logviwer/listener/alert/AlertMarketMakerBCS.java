package com.larrainvial.logviwer.listener.alert;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.Constants;
import com.larrainvial.logviwer.utils.Notifier;
import org.apache.log4j.Logger;

import java.util.logging.Level;

public class AlertMarketMakerBCS {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public AlertMarketMakerBCS(ModelRoutingData modelRoutingData, Algo algo){

        try {

            if (modelRoutingData.execType.equals("F")) {

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Trade MMBCS " +  modelRoutingData.symbol + "\n");
                stringBuffer.append("LastPx " + modelRoutingData.lastPx  + "\n");
                stringBuffer.append("LastQty " + modelRoutingData.lastQty  + "\n");

                Notifier.INSTANCE.notifyError(algo.nameAlgo, stringBuffer.toString());
            }


            if (modelRoutingData.messageByType.equals("8") &&
                    modelRoutingData.price * modelRoutingData.leavesQty < Constants.pxQxminBcs &&
                    !modelRoutingData.execType.equals("4")){

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("MMBCS " +  modelRoutingData.symbol + "\n");
                stringBuffer.append("Price "  + modelRoutingData.price + "\n");
                stringBuffer.append("Qty " + modelRoutingData.orderQty  + "\n");
                stringBuffer.append("LastPx " + modelRoutingData.lastPx  + "\n");
                stringBuffer.append("LastQty " + modelRoutingData.lastQty  + "\n");

                Notifier.INSTANCE.notifyError(algo.nameAlgo, stringBuffer.toString());
                Notifier.INSTANCE.notifyError(algo.nameAlgo, stringBuffer.toString());
                Notifier.INSTANCE.notifyError(algo.nameAlgo, stringBuffer.toString());
            }


        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }

    }


}
