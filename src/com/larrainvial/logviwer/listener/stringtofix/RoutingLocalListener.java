package com.larrainvial.logviwer.listener.stringtofix;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.utils.AlertEvent;
import com.larrainvial.logviwer.event.sendtoview.PositionViewEvent;
import com.larrainvial.logviwer.event.stringtofix.RoutingLocalEvent;
import com.larrainvial.logviwer.listener.alert.AlertMarketMakerBCSListener;
import com.larrainvial.logviwer.listener.calculate.CalculateMMBCSListener;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.*;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import org.apache.log4j.Logger;

import java.util.logging.Level;

public class RoutingLocalListener implements Listener {

    public Algo algo;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public RoutingLocalListener(Algo algo) {
        this.algo = algo;
    }


    @Override
    public synchronized void eventOccurred(Event event) {

        try {

            RoutingLocalEvent ev = (RoutingLocalEvent) event;

            if (ev.lineFromLog.equals(Constants.EMPTY)) return;
            if (!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;

            StringToRoutingData stringToRoutingData = new StringToRoutingData();
            ModelRoutingData modelRoutingData = stringToRoutingData.routing(ev.lineFromLog);

            Controller.dispatchEvent(new AlertEvent(algo, modelRoutingData, Constants.TypeMarket.ROUTING_LOCAL));

            if (algo.graphEnable) {
                Latency.latencyLocal(algo, modelRoutingData);
            }

            if (algo.nameAlgo.equals(Constants.MarketMakerBCS.NAME)){
                new CalculateMMBCSListener(algo, modelRoutingData);
                Controller.dispatchEvent(new PositionViewEvent(algo, modelRoutingData));

            } else {
                new CalculatePositions(algo, modelRoutingData);
                Controller.dispatchEvent(new PositionViewEvent(algo, modelRoutingData));
            }

        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }

    }

}
