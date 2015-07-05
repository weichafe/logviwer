package com.larrainvial.logviwer.listener.sendtoview;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.sendtoview.MarketDataAdrViewEvent;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class MarketDataAdrViewListener implements Listener {

    public Algo algo;

    public MarketDataAdrViewListener(Algo algo) {
        this.algo = algo;
    }


    @Override
    public synchronized void eventOccurred(Event event) {

        try {

            MarketDataAdrViewEvent ev = (MarketDataAdrViewEvent) event;

            if(!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;

            synchronized (algo.mkdAdrMasterList) {
                algo.mkdAdrMasterList.add(ev.modelMarketData);
                algo.mkdAdrTableView.setItems(algo.mkdAdrMasterList);
            }

        } catch (Exception e) {
            Helper.exception(e);
        }

    }

}
