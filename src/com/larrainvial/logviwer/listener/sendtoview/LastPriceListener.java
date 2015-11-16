package com.larrainvial.logviwer.listener.sendtoview;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import org.apache.log4j.Logger;
import java.util.Map;
import java.util.logging.Level;

public class LastPriceListener implements Runnable {

    public Algo algo;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    public ModelMarketData modelMarketData;

    public LastPriceListener(Algo algo, ModelMarketData modelMarketData){
        this.algo = algo;
        this.modelMarketData = modelMarketData;
    }

    @Override
    public void run() {

        try {

            if(!this.algo.nameAlgo.equals(algo.nameAlgo)) return;


            synchronized (algo.lastPriceMasterListHash) {

                for (Map.Entry<String, ModelMarketData> e : algo.lastPriceMasterListHash.entrySet()) {

                    ModelMarketData modelMarketData = algo.lastPriceMasterListHash.get(e.getKey());

                    if (!algo.lastPrice.containsKey(e.getKey())) {

                        algo.lastPriceTableView.getItems().add(algo.countLastPrice, modelMarketData);

                        algo.lastPrice.put(e.getKey(), algo.countLastPrice);
                        algo.countLastPrice++;

                    } else {
                        algo.lastPriceTableView.getItems().set(algo.lastPrice.get(e.getKey()), modelMarketData);
                    }

                }

            }

        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
        }



    }
}
