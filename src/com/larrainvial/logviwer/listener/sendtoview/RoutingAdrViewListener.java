package com.larrainvial.logviwer.listener.sendtoview;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.sendtoview.RoutingAdrViewEvent;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class RoutingAdrViewListener implements Listener {

    private Algo algo;

    @Override
    public void eventOccurred(Event event) {

        RoutingAdrViewEvent ev = (RoutingAdrViewEvent) event;

        try {

            algo = Repository.strategy.get(ev.nameAlgo);

            algo.routingAdrMasterList.add(ev.modelRoutingData);
            algo.routing_adr_tableView.setItems(algo.routingAdrMasterList);

        } catch (Exception e) {
            Helper.exception(e);
        }

    }
}
