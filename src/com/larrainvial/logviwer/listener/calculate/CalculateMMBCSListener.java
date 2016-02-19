package com.larrainvial.logviwer.listener.calculate;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.model.ModelMMBCS;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.Constants;
import org.apache.log4j.Logger;
import java.util.logging.Level;

public class CalculateMMBCSListener {

    private Algo algo;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private ModelRoutingData modelRoutingData;


    public CalculateMMBCSListener(Algo algo, ModelRoutingData modelRoutingData){

        try {

            this.algo = algo;
            this.modelRoutingData = modelRoutingData;
            this.calculatePositionsMMBCS();

        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
        }

    }


    public void calculatePositionsMMBCS() throws Exception {

        if (modelRoutingData.messageByType.equals("0") || modelRoutingData.messageByType.equals("5") ||
                modelRoutingData.messageByType.equals("1") || modelRoutingData.messageByType.equals("A")
                || modelRoutingData.messageByType.equals("9")) return;

        ModelMMBCS modelMMBCS;

        if (algo.mmBCSMasterListHash.containsKey(modelRoutingData.symbol)){
            modelMMBCS = algo.mmBCSMasterListHash.get(modelRoutingData.symbol);

        } else {
            algo.mmBCSMasterListHash.put(modelRoutingData.symbol, new ModelMMBCS());
            modelMMBCS = algo.mmBCSMasterListHash.get(modelRoutingData.symbol);
        }

        if (modelRoutingData.side.equals(Constants.BUY)) {

            modelMMBCS.symbol = modelRoutingData.symbol;
            modelMMBCS.priceBuy = modelRoutingData.price;
            modelMMBCS.orderQtyBuy = modelRoutingData.orderQty;
            modelMMBCS.leavesQtyBuy = modelRoutingData.leavesQty;
            modelMMBCS.cumQtyBuy = modelRoutingData.cumQty;
            modelMMBCS.lastPxBuy = modelRoutingData.lastPx;
            modelMMBCS.lastQtyBuy = modelRoutingData.lastQty;
            modelMMBCS.pxqBuy = Double.valueOf(Math.round(modelRoutingData.leavesQty * modelRoutingData.price));

        } else if (modelRoutingData.side.equals(Constants.SELL) || modelRoutingData.side.equals(Constants.SELL_SHORT)) {

            modelMMBCS.symbol = modelRoutingData.symbol;
            modelMMBCS.priceSell = modelRoutingData.price ;
            modelMMBCS.orderQtySell = modelRoutingData.orderQty;
            modelMMBCS.leavesQtySell = modelRoutingData.leavesQty;
            modelMMBCS.cumQtySell = modelRoutingData.cumQty;
            modelMMBCS.lastPxSell = modelRoutingData.lastPx;
            modelMMBCS.lastQtySell = modelRoutingData.lastQty;
            modelMMBCS.pxqSell = Double.valueOf(Math.round(modelRoutingData.leavesQty * modelRoutingData.price));

        }

    }

}
