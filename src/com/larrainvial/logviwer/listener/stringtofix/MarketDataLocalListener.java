package com.larrainvial.logviwer.listener.stringtofix;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.listener.alert.AlertListener;
import com.larrainvial.logviwer.listener.calculate.CalculateLastPriceListener;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.utils.Constants;
import com.larrainvial.logviwer.utils.StringToMarketData;
import org.apache.log4j.Logger;

import java.util.logging.Level;

public class MarketDataLocalListener extends Thread {

    public Algo algo;
    public String message;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public MarketDataLocalListener(Algo algo, String message) {
        this.algo = algo;
        this.message = message;
    }

    @Override
    public synchronized void run(){

        try {

            if (message.equals(Constants.EMPTY)) return;

            StringToMarketData stringToMarketData = new StringToMarketData();
            ModelMarketData modelMarketData = stringToMarketData.marketData(message);

            AlertListener alertListener = new AlertListener(algo, modelMarketData, Constants.TypeMarket.MKD_LOCAL);
            alertListener.start();

            CalculateLastPriceListener calculateLastPriceListener = new CalculateLastPriceListener(algo, modelMarketData, Constants.TypeMarket.MKD_LOCAL);
            calculateLastPriceListener.start();

        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
        }

    }

}
