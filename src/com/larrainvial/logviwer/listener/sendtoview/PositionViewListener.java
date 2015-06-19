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

                for (Map.Entry<String, ModelPositions> e: algo.getPositionsMasterListHash().entrySet()) {

                    try {

                        if (algo.getPositionsMasterListHash().containsKey(e.getKey())) {

                            if (e.getKey().equals(Helper.adrToLocal(ev.modelRoutingData.symbol))) {
                                algo.getPositionsMasterList().remove(algo.getPositionsMasterListHash().get(e.getKey()));
                                algo.getPositionsMasterList().add(algo.getPositionsMasterListHash().get(e.getKey()));
                                algo.getPanel_positions_tableView().setItems(algo.getPositionsMasterList());
                            }
                        }

                    } catch (Exception ex) {
                        Helper.exception(ex);
                    }
                }

                /*for (Map.Entry<String, ModelPositions> e : algo.getPositionsMasterListHash().entrySet()) {



                    ModelPositions modelPositions = algo.getPositionsMasterListHash().get(e.getKey());

                    if(algo.positionsMap.containsKey(e.getKey())){

                        ModelPositions modelPositionsMasterLis = algo.getPositionsMasterList().get(algo.positionsMap.get(e.getKey()));

                        modelPositionsMasterLis.qtyBuyLocal       = modelPositions.qtyBuyLocal;
                        modelPositionsMasterLis.qtyBuyAdr         = modelPositions.qtyBuyAdr;
                        modelPositionsMasterLis.qtySellLocal      = modelPositions.qtySellLocal;
                        modelPositionsMasterLis.qtySellAdr        = modelPositions.qtySellAdr;
                        modelPositionsMasterLis.qtySellLocalRatio = modelPositions.qtySellLocalRatio;
                        modelPositionsMasterLis.qtyBuyLocalRatio  = modelPositions.qtyBuyLocalRatio;

                    } else {

                        algo.getPositionsMasterList().add(algo.positionsMasterListHash.get(e.getKey()));
                        algo.positionsMap.put(e.getKey(), algo.contPositions);
                        algo.contPositions++;
                    }

                }

                algo.getPanel_positions_tableView().setItems(algo.getPositionsMasterList());
                */

            }


        } catch (Exception e) {
            Helper.exception(e);
        }

    }
}
