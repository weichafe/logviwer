package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.SendToViewEvent;
import com.larrainvial.logviwer.model.ModelPositions;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

import javax.swing.plaf.TableHeaderUI;
import java.util.Map;

public class SendToViewListener implements Listener {

    private Algo algo;

    @Override
    public void eventOccurred(Event event){

        SendToViewEvent ev = (SendToViewEvent) event;

        try {

            algo = Repository.strategy.get(ev.nameAlgo);

            if (ev.typeMarket.equals(algo.getMkd_dolar())) {
                algo.getDolarMasterList().add(ev.modelMarketData);
                algo.getMkd_dolar_tableView().setItems(algo.getDolarMasterList());
            }

            if (ev.typeMarket.equals(algo.getMkd_adr())) {
                algo.getMkdAdrMasterList().add(ev.modelMarketData);
                algo.getMkd_adr_tableView().setItems(algo.getMkdAdrMasterList());
            }

            if (ev.typeMarket.equals(algo.getMkd_local())) {
                algo.getMkdLocalMasterList().add(ev.modelMarketData);
                algo.getMkd_local_tableView().setItems(algo.getMkdLocalMasterList());

            }

            if (ev.typeMarket.equals(algo.getRouting_adr())) {
                algo.getRoutingAdrMasterList().add(ev.modelRoutingData);
                algo.getRouting_adr_tableView().setItems(algo.getRoutingAdrMasterList());
            }

            if (ev.typeMarket.equals(algo.getRouting_local())) {
                algo.getRoutingLocalMasterList().add(ev.modelRoutingData);
                algo.getRouting_local_tableView().setItems(algo.getRoutingLocalMasterList());

            }

            for (Map.Entry<String, ModelPositions> e: algo.getPositionsMasterListHash().entrySet()) {

                String symbol = Helper.adrToLocal(ev.modelRoutingData.symbol);

                if (algo.getPositionsMasterListHash().containsKey(e.getKey())) {
                    if (e.getKey().equals(symbol)) {
                        algo.getPositionsMasterList().remove(algo.getPositionsMasterListHash().get(e.getKey()));
                        algo.getPositionsMasterList().add(algo.getPositionsMasterListHash().get(e.getKey()));
                        algo.getPanel_positions_tableView().setItems(algo.getPositionsMasterList());
                    }
                }
            }

        }catch (Exception e){
            //new Algo().exception(e);
        }

    }

}