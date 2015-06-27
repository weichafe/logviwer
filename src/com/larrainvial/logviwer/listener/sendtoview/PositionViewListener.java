package com.larrainvial.logviwer.listener.sendtoview;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.sendtoview.PositionViewEvent;
import com.larrainvial.logviwer.model.ModelPositions;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

import java.util.Map;

public class PositionViewListener implements Listener {

    private Algo algo;

    @Override
    public void eventOccurred(Event event) {

        PositionViewEvent ev = (PositionViewEvent) event;

        try {

            algo = Repository.strategy.get(ev.nameAlgo);

            synchronized(algo.positionsMasterListHash) {

                for (Map.Entry<String, ModelPositions> e: algo.positionsMasterListHash.entrySet()) {

                    try {

                        if (algo.positionsMasterListHash.containsKey(e.getKey())) {

                            if (e.getKey().equals(Helper.adrToLocal(ev.modelRoutingData.symbol))) {
                                algo.positionsMasterList.remove(algo.positionsMasterListHash.get(e.getKey()));
                                algo.positionsMasterList.add(algo.positionsMasterListHash.get(e.getKey()));
                                algo.panel_positions_tableView.setItems(algo.positionsMasterList);
                            }
                        }

                    } catch (Exception ex) {
                        Helper.exception(ex);
                    }
                }

                /*for (Map.Entry<String, ModelPositions> e : algo.positionsMasterListHash.entrySet()) {



                    ModelPositions modelPositions = algo.positionsMasterListHash.get(e.getKey());

                    if(algo.positionsMap.containsKey(e.getKey())){

                        ModelPositions modelPositionsMasterLis = algo.positionsMasterList.get(algo.positionsMap.get(e.getKey()));

                        modelPositionsMasterLis.qtyBuyLocal       = modelPositions.qtyBuyLocal;
                        modelPositionsMasterLis.qtyBuyAdr         = modelPositions.qtyBuyAdr;
                        modelPositionsMasterLis.qtySellLocal      = modelPositions.qtySellLocal;
                        modelPositionsMasterLis.qtySellAdr        = modelPositions.qtySellAdr;
                        modelPositionsMasterLis.qtySellLocalRatio = modelPositions.qtySellLocalRatio;
                        modelPositionsMasterLis.qtyBuyLocalRatio  = modelPositions.qtyBuyLocalRatio;

                    } else {

                        algo.positionsMasterList.add(algo.positionsMasterListHash.get(e.getKey()));
                        algo.positionsMap.put(e.getKey(), algo.contPositions);
                        algo.contPositions++;
                    }

                }

                algo.panel_positions_tableView.setItems(algo.positionsMasterList);
                */

            }


        } catch (Exception e) {
            Helper.exception(e);
        }

    }
}
