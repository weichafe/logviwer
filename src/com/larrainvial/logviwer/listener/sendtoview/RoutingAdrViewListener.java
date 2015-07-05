package com.larrainvial.logviwer.listener.sendtoview;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.sendtoview.RoutingAdrViewEvent;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class RoutingAdrViewListener implements Listener {

    public Algo algo;

    public RoutingAdrViewListener(Algo algo) {
        this.algo = algo;
    }


    @Override
    public synchronized void eventOccurred(Event event) {

        try {

            RoutingAdrViewEvent ev = (RoutingAdrViewEvent) event;

            if(!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;

            synchronized (algo.routingAdrMasterList) {
                algo.routingAdrMasterList.add(ev.modelRoutingData);
                algo.routingAdrTableView.setItems(algo.routingAdrMasterList);
            }

        } catch (Exception e) {
            Helper.exception(e);
        }

    }



}
