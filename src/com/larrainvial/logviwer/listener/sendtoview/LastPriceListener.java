package com.larrainvial.logviwer.listener.sendtoview;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.sendtoview.LastPriceEvent;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.fxvo.Dialog;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.logging.Level;

public class LastPriceListener implements Listener {

    public Algo algo;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public LastPriceListener(Algo algo){
        this.algo = algo;
    }

    @Override
    public void eventOccurred(Event event) {

        try {

            LastPriceEvent ev = (LastPriceEvent) event;

            if(!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;


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
