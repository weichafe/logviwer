package com.larrainvial.logviwer.listener.sendtoview;


import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.sendtoview.LastPriceEvent;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelPositions;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

import java.util.Map;

public class LastPriceListener implements Listener {

    public Algo algo;

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


                /*
                for (Map.Entry<String, ModelMarketData> e : algo.lastPriceMasterListHash.entrySet()) {

                    try {

                        if (algo.lastPriceMasterListHash.containsKey(e.getKey())) {

                            algo.lastPriceMasterList.remove(algo.lastPriceMasterListHash.get(e.getKey()));
                            algo.lastPriceMasterList.add(algo.lastPriceMasterListHash.get(e.getKey()));
                            algo.lastPriceTableView.setItems(algo.lastPriceMasterList);

                        }

                    } catch (Exception ex) {
                        Helper.exception(ex);
                    }
                }

                */
            }

        } catch (Exception e) {
            Helper.exception(e);
        }



    }
}
