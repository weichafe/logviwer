package com.larrainvial.logviwer.listener.stringtofix;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.AlertEvent;
import com.larrainvial.logviwer.event.sendtoview.PositionViewEvent;
import com.larrainvial.logviwer.event.sendtoview.RoutingAdrViewEvent;
import com.larrainvial.logviwer.event.stringtofix.RoutingAdrEvent;
import com.larrainvial.logviwer.model.ModelMarketData;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.CalculatePositions;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.StringToRoutingData;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class RoutingAdrListener implements Listener {

    private ModelRoutingData modelRoutingData;
    private ModelMarketData modelMarketData;
    private Algo algo;

    @Override
    public void eventOccurred(Event event) {

        try {

            RoutingAdrEvent ev = (RoutingAdrEvent) event;

            if (ev.lineFromLog.equals("")) return;

            algo = Repository.strategy.get(ev.nameAlgo);

            StringToRoutingData stringToRoutingData = new StringToRoutingData();
            modelRoutingData = stringToRoutingData.routing(ev.lineFromLog);

            Controller.dispatchEvent(new RoutingAdrViewEvent(this, ev.nameAlgo, ev.typeMarket, modelRoutingData));
            Controller.dispatchEvent(new PositionViewEvent(this, ev.nameAlgo, ev.typeMarket, modelRoutingData));

            Controller.dispatchEvent(new AlertEvent(this, ev.nameAlgo, ev.typeMarket, modelRoutingData, ev.lineFromLog));

            if (modelRoutingData.execType.equals("Trade")) {
                 new CalculatePositions(algo, modelRoutingData);
            }

        } catch (Exception e){
            Helper.exception(e);
        }

    }

}