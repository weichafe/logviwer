package com.larrainvial.logviwer.utils;

import com.larrainvial.logviwer.Algo;
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

            if (!modelRoutingData.execType.equals(Constants.TRADE)) return;

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
            positions.localSymbol = keyHashPositions;
            positions.positions = helper.positions(keyHashPositions);
        }

        if (modelRoutingData.side.equals("Buy")) {

            if (helper.local(modelRoutingData.symbol) && !modelRoutingData.exDestination.equals("SMART") && !modelRoutingData.exDestination.equals("US")) {

                positions.inflowLocal = modelRoutingData.lastQty / positions.ratio + positions.inflowLocal;
                positions.localBuy = modelRoutingData.lastQty + positions.localBuy;
                positions.localSymbol = modelRoutingData.getSymbol();


            } else {

                positions.flobackSell = modelRoutingData.lastQty + positions.flobackSell;
                positions.adrSymbol = modelRoutingData.getSymbol();
                positions.leaveFlowback = modelRoutingData.getLeavesQty();
            }
        }

        if (modelRoutingData.side.equals("Sell") || modelRoutingData.side.equals("Sell Short")) {

            if (helper.local(modelRoutingData.symbol) && !modelRoutingData.exDestination.equals("SMART") && !modelRoutingData.exDestination.equals("US")) {

                positions.flowbackLocal = modelRoutingData.lastQty / positions.ratio + positions.flowbackLocal;
                positions.localSell = modelRoutingData.lastQty + positions.localSell;
                positions.localSymbol = modelRoutingData.getSymbol();


            } else {

                positions.inflowAdr = modelRoutingData.lastQty + positions.inflowAdr;
                positions.adrSymbol = modelRoutingData.getSymbol();
                positions.leaveInflow = modelRoutingData.getLeavesQty();

            }

        }

        positions.differenceInflow = Math.round((positions.inflowLocal - positions.inflowAdr));
        positions.differenceflowback = Math.round((positions.flowbackLocal - positions.flobackSell));

    }
}