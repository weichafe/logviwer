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

    }
}
