package com.larrainvial.logviwer.listener.sendtoview;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.sendtoview.RoutingLocalViewEvent;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class RoutingLocalViewListener implements Listener {

    private Algo algo;

    @Override
    public void eventOccurred(Event event) {

        try {

            RoutingLocalViewEvent ev = (RoutingLocalViewEvent) event;

            algo = Repository.strategy.get(ev.nameAlgo);

            algo.getRoutingLocalMasterList().add(ev.modelRoutingData);
            algo.getRouting_local_tableView().setItems(algo.getRoutingLocalMasterList());

        } catch (Exception e) {
            Helper.exception(e);
        }

    }

}