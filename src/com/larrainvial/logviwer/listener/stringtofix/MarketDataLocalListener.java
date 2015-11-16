package com.larrainvial.logviwer.listener.stringtofix;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.listener.util.AlertListener;
import com.larrainvial.logviwer.listener.util.CalculateLastPriceListener;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.utils.StringToMarketData;
import com.larrainvial.trading.emp.Controller;
import org.apache.log4j.Logger;

import java.util.logging.Level;

public class MarketDataLocalListener implements Runnable {

    public Algo algo;
    public final String TYPE_MARKET = "MKD LOCAL";
    private Logger logger = Logger.getLogger(this.getClass().getName());
    public String message;

    public MarketDataLocalListener(Algo algo, String message) {
        this.algo = algo;
        this.message = message;
    }


    @Override
    public synchronized void run() {

        try {

            if (this.message.equals("")) return;

            StringToMarketData stringToMarketData = new StringToMarketData();
            ModelMarketData modelMarketData = stringToMarketData.marketData(message);

            new Thread(new AlertListener(algo, modelMarketData, TYPE_MARKET)).start();
            new Thread(new CalculateLastPriceListener(algo, modelMarketData, TYPE_MARKET)).start();

        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
        }

    }

}
