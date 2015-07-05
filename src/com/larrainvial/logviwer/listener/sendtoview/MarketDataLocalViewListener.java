package com.larrainvial.logviwer.listener.sendtoview;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.sendtoview.MarketDataLocalViewEvent;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class MarketDataLocalViewListener implements Listener {

    public Algo algo;

    public MarketDataLocalViewListener(Algo algo) {
        this.algo = algo;
    }

    @Override
    public synchronized void eventOccurred(Event event) {

        try {

            MarketDataLocalViewEvent ev = (MarketDataLocalViewEvent) event;

            if(!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;

            synchronized (algo.mkdLocalMasterList){
                algo.mkdLocalMasterList.add(ev.modelMarketData);
                algo.mkdLocalTableView.setItems(algo.mkdLocalMasterList);
            }


        } catch (Exception e) {
            Helper.exception(e);
        }

    }

}
