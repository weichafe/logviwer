package com.larrainvial.logviwer.listener.stringtofix;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.utils.AlertEvent;
import com.larrainvial.logviwer.event.utils.CalculateLastPriceEvent;
import com.larrainvial.logviwer.event.stringtofix.DolarEvent;
import com.larrainvial.logviwer.listener.sendtoview.LastPriceListener;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.fxvo.Dialog;
import com.larrainvial.logviwer.utils.Constants;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.logviwer.utils.StringToMarketData;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import org.apache.log4j.Logger;

import java.util.logging.Level;

public class DolarListener implements Listener {

    public Algo algo;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public DolarListener(Algo algo) {
        this.algo = algo;
    }


    @Override
    public synchronized void eventOccurred(Event event) {

        try {

            DolarEvent ev = (DolarEvent) event;

            if (ev.lineFromLog.equals(Constants.EMPTY)) return;
            if (!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;

            StringToMarketData stringToMarketData = new StringToMarketData();
            ModelMarketData modelMarketData = stringToMarketData.marketData(ev.lineFromLog);

            Controller.dispatchEvent(new AlertEvent(algo, modelMarketData, Constants.TypeMarket.DOLAR));
            Controller.dispatchEvent(new CalculateLastPriceEvent(algo, modelMarketData, Constants.TypeMarket.DOLAR));

        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
        }

    }

}
