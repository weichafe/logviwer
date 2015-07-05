package com.larrainvial.logviwer.listener.sendtoview;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.sendtoview.RoutingLocalViewEvent;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class RoutingLocalViewListener implements Listener {

    public Algo algo;
    public ModelRoutingData modelRoutingData;

    public RoutingLocalViewListener(Algo algo) {
        this.algo = algo;
    }


    @Override
    public synchronized void eventOccurred(Event event) {

        try {

            RoutingLocalViewEvent ev = (RoutingLocalViewEvent) event;

            if(!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;

            synchronized (algo.routingLocalMasterList) {
                algo.routingLocalMasterList.add(ev.modelRoutingData);
                algo.routingLocalTableView.setItems(algo.routingLocalMasterList);
            }

        } catch (Exception e) {
            Helper.exception(e);
        }

    }

}
