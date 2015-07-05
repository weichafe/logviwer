package com.larrainvial.logviwer.listener.sendtoview;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.sendtoview.DolarViewEvent;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class DolarViewListener implements Listener {

    public Algo algo;

    public DolarViewListener(Algo algo) {
        this.algo = algo;
    }


    @Override
    public synchronized void eventOccurred(Event event) {

        try {

            DolarViewEvent ev = (DolarViewEvent) event;

            if(!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;

            synchronized (algo.dolarMasterList) {
                algo.dolarMasterList.add(ev.modelMarketData);
                algo.mkdDolarTableView.setItems(algo.dolarMasterList);
            }


        } catch (Exception e) {
            Helper.exception(e);
        }

    }



}
