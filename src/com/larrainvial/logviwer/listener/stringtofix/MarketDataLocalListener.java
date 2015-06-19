package com.larrainvial.logviwer.listener.stringtofix;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.AlertEvent;
import com.larrainvial.logviwer.event.sendtoview.MarketDataLocalViewEvent;
import com.larrainvial.logviwer.event.stringtofix.MarketDataLocalEvent;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.StringToMarketData;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class MarketDataLocalListener implements Listener {

    private ModelRoutingData modelRoutingData;
    private ModelMarketData modelMarketData;
    private Algo algo;

    @Override
    public void eventOccurred(Event event) {

        try {

            MarketDataLocalEvent ev = (MarketDataLocalEvent) event;

            if (ev.lineFromLog.equals("")) return;


            algo = Repository.strategy.get(ev.nameAlgo);

            StringToMarketData stringToMarketData = new StringToMarketData();
            modelMarketData = stringToMarketData.marketData(ev.lineFromLog);

            Controller.dispatchEvent(new MarketDataLocalViewEvent(this, ev.nameAlgo, ev.typeMarket, modelMarketData));
            Controller.dispatchEvent(new AlertEvent(this, ev.nameAlgo, ev.typeMarket, modelMarketData, ev.lineFromLog));

        } catch (Exception e){
            Helper.exception(e);
        }

    }

}
