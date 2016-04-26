package com.larrainvial.logviwer.listener.stringtofix;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.utils.AlertEvent;
import com.larrainvial.logviwer.event.sendtoview.PositionViewEvent;
import com.larrainvial.logviwer.event.stringtofix.RoutingAdrEvent;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.*;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import org.apache.log4j.Logger;
import java.util.logging.Level;

public class RoutingAdrListener implements Listener {

    public Algo algo;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public RoutingAdrListener(Algo algo) {
        this.algo = algo;
    }

    @Override
    public synchronized void eventOccurred(Event event) {

        try {

            RoutingAdrEvent ev = (RoutingAdrEvent) event;

            if (ev.lineFromLog.equals(Constants.EMPTY)) return;
            if (!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;

            StringToRoutingData stringToRoutingData = new StringToRoutingData();

            ModelRoutingData modelRoutingData = stringToRoutingData.routing(ev.lineFromLog);

            Controller.dispatchEvent(new PositionViewEvent(algo, modelRoutingData));
            Controller.dispatchEvent(new AlertEvent(algo, modelRoutingData, Constants.TypeMarket.ROUTING_ADR));

             new CalculatePositions(Repository.strategy.get(ev.algo.nameAlgo), modelRoutingData);


            if (algo.graphEnable) {
                Latency.latencyADR(algo, modelRoutingData);
            }

        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }

    }

}
