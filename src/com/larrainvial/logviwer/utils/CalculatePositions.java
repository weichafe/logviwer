package com.larrainvial.logviwer.utils;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.fxvo.Dialog;
import com.larrainvial.logviwer.model.ModelPositions;
import com.larrainvial.logviwer.model.ModelRoutingData;
import org.apache.log4j.Logger;

import java.util.logging.Level;

public class CalculatePositions {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private ModelRoutingData modelRoutingData;
    private Algo algo;

    public CalculatePositions(Algo algo, ModelRoutingData modelRoutingData) {

        try {

            this.modelRoutingData = modelRoutingData;
            this.algo = algo;
            this.calculatePositions();

        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
        }
    }


    public void calculatePositions() throws Exception {

        ModelPositions positions;
        Helper helper = new Helper();

        String keyHashPositions = helper.adrToLocal(modelRoutingData.symbol);

        if (algo.positionsMasterListHash.containsKey(keyHashPositions)) {
            positions = algo.positionsMasterListHash.get(keyHashPositions);

        } else {

            algo.positionsMasterListHash.put(keyHashPositions, new ModelPositions());
            positions = algo.positionsMasterListHash.get(keyHashPositions);
            positions.ratio = helper.ratio(keyHashPositions);
            positions.symbolLocal = keyHashPositions;
            positions.positions = helper.positions(keyHashPositions);
        }

        if (modelRoutingData.side.equals("Buy")) {

            if (helper.local(modelRoutingData.symbol) && !modelRoutingData.exDestination.equals("SMART") && !modelRoutingData.exDestination.equals("US")) {

                positions.qtyBuyLocalRatio = modelRoutingData.lastQty / positions.ratio + positions.qtyBuyLocalRatio;
                positions.qtyBuyLocal = modelRoutingData.lastQty + positions.qtyBuyLocal;
                positions.symbolLocal = modelRoutingData.getSymbol();

            } else {

                positions.qtyBuyAdr = modelRoutingData.lastQty + positions.qtyBuyAdr;
                positions.symbolAdr = modelRoutingData.getSymbol();

            }
        }

        if (modelRoutingData.side.equals("Sell") || modelRoutingData.side.equals("Sell Short")) {

            if (helper.local(modelRoutingData.symbol) && !modelRoutingData.exDestination.equals("SMART") && !modelRoutingData.exDestination.equals("US")) {

                positions.qtySellLocalRatio = modelRoutingData.lastQty / positions.ratio + positions.qtySellLocalRatio;
                positions.qtySellLocal = modelRoutingData.lastQty + positions.qtySellLocal;
                positions.symbolLocal = modelRoutingData.getSymbol();


            } else {

                positions.qtySellAdr = modelRoutingData.lastQty + positions.qtySellAdr;
                positions.symbolAdr = modelRoutingData.getSymbol();

            }

        }

        positions.differenceInflow = Math.round((positions.qtyBuyLocalRatio - positions.qtySellAdr));
        positions.differenceflowback = Math.round((positions.qtySellLocalRatio - positions.qtyBuyAdr));

    }
}