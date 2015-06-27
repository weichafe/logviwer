package com.larrainvial.logviwer.listener.sendtoview;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.sendtoview.DolarViewEvent;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class DolarViewListener implements Listener {

    private Algo algo;

    @Override
    public void eventOccurred(Event event) {

        DolarViewEvent ev = (DolarViewEvent) event;

        try {

            algo = Repository.strategy.get(ev.nameAlgo);

            algo.dolarMasterList.add(ev.modelMarketData);
            algo.mkd_dolar_tableView.setItems(algo.dolarMasterList);

            if(algo.dolarMasterList.size() >= 10000){
                   algo.dolarMasterList.clear();
            }

        } catch (Exception e) {
            Helper.exception(e);
        }

    }


}
