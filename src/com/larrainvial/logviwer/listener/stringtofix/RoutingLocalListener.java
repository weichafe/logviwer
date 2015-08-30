package com.larrainvial.logviwer.listener.stringtofix;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.AlertEvent;
import com.larrainvial.logviwer.event.sendtoview.PositionViewEvent;
import com.larrainvial.logviwer.event.stringtofix.RoutingLocalEvent;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.CalculatePositions;
import com.larrainvial.logviwer.fxvo.Dialog;
import com.larrainvial.logviwer.utils.Latency;
import com.larrainvial.logviwer.utils.StringToRoutingData;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class RoutingLocalListener implements Listener {

    public Algo algo;
    public final String TYPE_MARKET = "ROUTING LOCAL";
    public RoutingLocalListener(Algo algo) {
        this.algo = algo;
    }


    @Override
    public synchronized void eventOccurred(Event event) {

        try {

            RoutingLocalEvent ev = (RoutingLocalEvent) event;

            if (ev.lineFromLog.equals("")) return;
            if(!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;

            StringToRoutingData stringToRoutingData = new StringToRoutingData();
            ModelRoutingData modelRoutingData = stringToRoutingData.routing(ev.lineFromLog);

            Controller.dispatchEvent(new PositionViewEvent(algo, modelRoutingData));
            Controller.dispatchEvent(new AlertEvent(algo, modelRoutingData, TYPE_MARKET));

            if (modelRoutingData.execType.equals("Trade")) {
                 new CalculatePositions(algo, modelRoutingData);
            }

            Latency.latencyLocal(algo, modelRoutingData);


        } catch (Exception e) {
            Dialog.exception(e);
        }

    }

}
