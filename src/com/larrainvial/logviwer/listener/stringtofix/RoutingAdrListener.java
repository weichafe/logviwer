package com.larrainvial.logviwer.listener.stringtofix;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.utils.AlertEvent;
import com.larrainvial.logviwer.event.sendtoview.PositionViewEvent;
import com.larrainvial.logviwer.event.stringtofix.RoutingAdrEvent;
import com.larrainvial.logviwer.event.utils.CalculatePositionsEvent;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.fxvo.Dialog;
import com.larrainvial.logviwer.utils.*;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import org.apache.log4j.Logger;

public class RoutingAdrListener implements Listener {

    public Algo algo;
    public final String TYPE_MARKET = "ROUTING ADR";
    private static Logger logger = Logger.getLogger(RoutingAdrListener.class.getName());

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

            Controller.dispatchEvent(new PositionViewEvent(algo, modelRoutingData));
            Controller.dispatchEvent(new AlertEvent(algo, modelRoutingData, TYPE_MARKET));

            if (modelRoutingData.execType.equals("Trade")) {
                new CalculatePositions(Repository.strategy.get(ev.algo.nameAlgo), modelRoutingData);
            }

            if (algo.graphEnable) {
                Latency.latencyADR(algo, modelRoutingData);
            }

        } catch (Exception e){
            logger.error(e);
        }

    }

}
