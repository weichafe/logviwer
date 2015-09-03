package com.larrainvial.logviwer.listener.stringtofix;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.utils.AlertEvent;
import com.larrainvial.logviwer.event.utils.CalculateLastPriceEvent;
import com.larrainvial.logviwer.event.stringtofix.MarketDataADREvent;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.fxvo.Dialog;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.logviwer.utils.StringToMarketData;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class MarketDataAdrListener implements Listener {

    public Algo algo;
    public final String TYPE_MARKET = "MKD ADR";
    public MarketDataAdrListener(Algo algo) {
        this.algo = algo;
    }


    @Override
    public synchronized void eventOccurred(Event event) {

        try {

            MarketDataADREvent ev = (MarketDataADREvent) event;

            if (ev.lineFromLog.equals("") ) return;
            if(!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;

            StringToMarketData stringToMarketData = new StringToMarketData();
            ModelMarketData modelMarketData = stringToMarketData.marketData(ev.lineFromLog);

            Controller.dispatchEvent(new AlertEvent(algo, modelMarketData, TYPE_MARKET));

            Controller.dispatchEvent(new CalculateLastPriceEvent(algo, modelMarketData, TYPE_MARKET));

            //new CalculateLastPrice(algo, modelMarketData, TYPE_MARKET);

        } catch (Exception e){
            e.printStackTrace();
            Helper.printerLog(e.getMessage().toString());
            Notifier.INSTANCE.notifyError("Error", e.getMessage().toString());
        }

    }


}
