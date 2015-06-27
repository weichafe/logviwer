package com.larrainvial.logviwer.listener.sendtoview;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.sendtoview.MarketDataLocalViewEvent;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class MarketDataLocalViewListener implements Listener {

    private Algo algo;

    @Override
    public void eventOccurred(Event event) {

        try {

            MarketDataLocalViewEvent ev = (MarketDataLocalViewEvent) event;

            algo = Repository.strategy.get(ev.nameAlgo);

            synchronized (algo.mkdLocalMasterList){
                algo.mkdLocalMasterList.add(ev.modelMarketData);
                algo.mkd_local_tableView.setItems(algo.mkdLocalMasterList);
            }

        } catch (Exception e) {
            Helper.exception(e);
        }

    }
}
