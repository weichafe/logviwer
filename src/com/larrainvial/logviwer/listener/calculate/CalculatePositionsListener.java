package com.larrainvial.logviwer.listener.calculate;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.utils.CalculatePositionsEvent;
import com.larrainvial.logviwer.model.ModelPositions;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import org.apache.log4j.Logger;

import java.util.logging.Level;

public class CalculatePositionsListener implements Listener {

    public Algo algo;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public CalculatePositionsListener(Algo algo){
        this.algo = algo;
    }

    @Override
    public void eventOccurred(Event event) {

        try {

            CalculatePositionsEvent ev = (CalculatePositionsEvent) event;

            if (!ev.algo.equals(this.algo)) return;

            this.calculatePositions(ev.modelRoutingData);


        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
            logger.error(algo.nameAlgo);
        }

    }

    public void calculatePositions(ModelRoutingData modelRoutingData) throws Exception {

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
                positions.leaveInflow = modelRoutingData.getLeavesQty();
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
                positions.leaveFlowback = modelRoutingData.getLeavesQty();

            }

        }

    }
}
