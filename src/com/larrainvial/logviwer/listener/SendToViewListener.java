package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.SendToViewEvent;
import com.larrainvial.logviwer.model.ModelPositions;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Map;

public class SendToViewListener implements Listener {

    private Algo algo;

    @Override
    public void eventOccurred(Event event){

        try {

            SendToViewEvent ev = (SendToViewEvent) event;

            algo = Repository.strategy.get(ev.nameAlgo);

            if(ev.typeMarket.equals(algo.getMkd_dolar())){
                algo.getDolarMasterList().add(ev.modelMarketData);
                algo.getMkd_dolar_tableView().setItems(algo.getDolarMasterList());
            }

            if(ev.typeMarket.equals(algo.getMkd_adr())){
                algo.getMkdAdrMasterList().add(ev.modelMarketData);
                algo.getMkd_adr_tableView().setItems(algo.getMkdAdrMasterList());
            }

            if(ev.typeMarket.equals(algo.getMkd_local())){
                algo.getMkdLocalMasterList().add(ev.modelMarketData);
                algo.getMkd_local_tableView().setItems(algo.getMkdLocalMasterList());

            }

            if(ev.typeMarket.equals(algo.getRouting_adr())){
                algo.getRoutingLocalMasterList().add(ev.modelRoutingData);
                algo.getRouting_adr_tableView().setItems(algo.getRoutingLocalMasterList());
            }

            if(ev.typeMarket.equals(algo.getRouting_local())){
                algo.getRoutingAdrMasterList().add(ev.modelRoutingData);
                algo.getRouting_local_tableView().setItems(algo.getRoutingAdrMasterList());

            }

            ObservableList<ModelPositions> positionsMasterList = FXCollections.observableArrayList();
            positionsMasterList.clear();

            for (Map.Entry<String, ModelPositions> e: algo.getPositionsMasterListHash().entrySet()) {

                if (algo.getPositionsMasterListHash().containsKey(e.getKey())) {
                    positionsMasterList.add(algo.getPositionsMasterListHash().get(e.getKey()));
                }
            }

            algo.getPanel_positions_tableView().setItems(positionsMasterList);


        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
