package com.larrainvial.logviwer.utils;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.model.ModelPositions;
import com.larrainvial.logviwer.model.ModelRoutingData;

public class CalculatePositions {

    private static ModelPositions positions;
    private ModelRoutingData modelRoutingData;
    private Algo algo;

    public CalculatePositions(Algo algo, ModelRoutingData modelRoutingData) {

        this.positions = new ModelPositions();
        this.modelRoutingData = modelRoutingData;
        this.algo = algo;
        this.calculatePositions();
    }


   public void calculatePositions(){

       String keyHashPositions = Helper.adrToLocal(modelRoutingData.symbol);

       if (algo.positions.containsKey(keyHashPositions)) {
           positions = algo.positions.get(keyHashPositions);

       } else {
           algo.positions.put(keyHashPositions, new ModelPositions());
           positions = algo.positions.get(keyHashPositions);
           positions.ratio = Helper.ratio(keyHashPositions);
           positions.symbolLocal = keyHashPositions;
       }


       if (modelRoutingData.side.equals("1")) {

           if (Helper.local(modelRoutingData.symbol)) {
               positions.qtyBuyLocalRatio = modelRoutingData.lastQty / positions.ratio + positions.qtyBuyLocal ;
               positions.qtyBuyLocal= modelRoutingData.lastQty + positions.qtyBuyLocal ;
               positions.symbolLocal = modelRoutingData.getSymbol();

           } else {
               positions.qtyBuyAdr = modelRoutingData.lastQty + positions.qtyBuyAdr;
               positions.symbolAdr = modelRoutingData.getSymbol();
           }

       }

       if (modelRoutingData.side.equals("2") || modelRoutingData.side.equals("5")) {

           if (Helper.local(modelRoutingData.symbol)) {
               positions.qtySellLocalRatio = modelRoutingData.lastQty / positions.ratio + positions.qtySellLocal;
               positions.qtySellLocal = modelRoutingData.lastQty + positions.qtySellLocal;
               positions.symbolLocal= modelRoutingData.getSymbol();

           } else {
               positions.qtySellAdr = modelRoutingData.lastQty + positions.qtySellAdr;
               positions.symbolAdr = modelRoutingData.getSymbol();
           }

       }

   }
}
