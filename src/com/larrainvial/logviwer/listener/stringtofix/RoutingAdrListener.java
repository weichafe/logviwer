package com.larrainvial.logviwer.listener.stringtofix;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.AlertEvent;
import com.larrainvial.logviwer.event.sendtoview.PositionViewEvent;
import com.larrainvial.logviwer.event.sendtoview.RoutingAdrViewEvent;
import com.larrainvial.logviwer.event.stringtofix.RoutingAdrEvent;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.CalculatePositions;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.StringToRoutingData;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class RoutingAdrListener implements Listener {

    public Algo algo;

    public RoutingAdrListener(Algo algo) {
        this.algo = algo;
    }

    @Override
    public synchronized void eventOccurred(Event event) {

        try {

            RoutingAdrEvent ev = (RoutingAdrEvent) event;

            if (ev.lineFromLog.equals("")) return;
            if(!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;


            StringToRoutingData stringToRoutingData = new StringToRoutingData();
            ModelRoutingData modelRoutingData = stringToRoutingData.routing(ev.lineFromLog);

            Controller.dispatchEvent(new RoutingAdrViewEvent(algo, modelRoutingData));
            Controller.dispatchEvent(new PositionViewEvent(algo, modelRoutingData));

            Controller.dispatchEvent(new AlertEvent(algo, modelRoutingData));

            if (modelRoutingData.execType.equals("Trade")) {
                 new CalculatePositions(Repository.strategy.get(ev.algo.nameAlgo), modelRoutingData);
            }

        } catch (Exception e){
            Helper.exception(e);
        }

    }

}
