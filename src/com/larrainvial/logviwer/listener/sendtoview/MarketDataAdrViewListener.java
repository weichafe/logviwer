package com.larrainvial.logviwer.listener.sendtoview;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.sendtoview.MarketDataAdrViewEvent;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class MarketDataAdrViewListener implements Listener {

    private Algo algo;

    @Override
    public void eventOccurred(Event event) {

        try {

            MarketDataAdrViewEvent ev = (MarketDataAdrViewEvent) event;

            algo = Repository.strategy.get(ev.nameAlgo);

            algo.getMkdAdrMasterList().add(ev.modelMarketData);
            algo.getMkd_adr_tableView().setItems(algo.getMkdAdrMasterList());

        } catch (Exception e) {
            Helper.exception(e);
        }

    }
}
