package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.SendToViewEvent;
import com.larrainvial.logviwer.model.ModelPositions;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

import java.util.Map;

public class SendToViewListener implements Listener {

    private Algo algo;

    @Override
    public void eventOccurred(Event event) {

        SendToViewEvent ev = (SendToViewEvent) event;

        try {

            algo = Repository.strategy.get(ev.nameAlgo);

            if (ev.typeMarket.equals(algo.mkd_dolar)) {
                algo.dolarMasterList.add(ev.modelMarketData);
                algo.mkd_dolar_tableView.setItems(algo.dolarMasterList);

                if(algo.dolarMasterList.size() >= 10000){
                    algo.dolarMasterList.clear();
                }
            }

            if (ev.typeMarket.equals(algo.mkd_adr)) {
                algo.mkdAdrMasterList.add(ev.modelMarketData);
                algo.mkd_adr_tableView.setItems(algo.mkdAdrMasterList);
            }

            if (ev.typeMarket.equals(algo.mkd_local)) {
                algo.mkdLocalMasterList.add(ev.modelMarketData);
                algo.mkd_local_tableView.setItems(algo.mkdLocalMasterList);

            }

            if (ev.typeMarket.equals(algo.routing_adr)) {
                algo.routingAdrMasterList.add(ev.modelRoutingData);
                algo.routing_adr_tableView.setItems(algo.routingAdrMasterList);
            }

            if (ev.typeMarket.equals(algo.routing_local)) {
                algo.routingLocalMasterList.add(ev.modelRoutingData);
                algo.routing_local_tableView.setItems(algo.routingLocalMasterList);

            }

            if(!ev.marketData) return;


            synchronized(algo.positionsMasterListHash) {

                for (Map.Entry<String, ModelPositions> e : algo.positionsMasterListHash.entrySet()) {

                    if (algo.positionsMasterListHash.containsKey(e.getKey()))

                        if (e.getKey().equals(Helper.adrToLocal(ev.modelRoutingData.symbol))) {

                            algo.positionsMasterList.remove(algo.positionsMasterListHash.get(e.getKey()));
                            algo.positionsMasterList.add(algo.positionsMasterListHash.get(e.getKey()));
                            algo.panel_positions_tableView.setItems(algo.positionsMasterList);

                        }
                }
            }


        } catch (Exception e) {
            Helper.exception(e);
        }

    }

}