package com.larrainvial.logviwer.listener.stringtofix;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.listener.sendtoview.PositionViewListener;
import com.larrainvial.logviwer.listener.util.AlertListener;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.CalculatePositions;
import com.larrainvial.logviwer.utils.Latency;
import com.larrainvial.logviwer.utils.StringToRoutingData;
import com.larrainvial.trading.emp.Controller;
import org.apache.log4j.Logger;

import java.util.logging.Level;

public class RoutingAdrListener implements Runnable {

    public Algo algo;
    public String message;
    public final String TYPE_MARKET = "ROUTING ADR";
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public RoutingAdrListener(Algo algo, String message) {
        this.algo = algo;
        this.message = message;
    }

    @Override
    public synchronized void run() {

        try {


            if (this.message.equals("")) return;
            if(!this.algo.nameAlgo.equals(algo.nameAlgo)) return;

            StringToRoutingData stringToRoutingData = new StringToRoutingData();
            ModelRoutingData modelRoutingData = stringToRoutingData.routing(this.message);

            new Thread(new PositionViewListener(algo, modelRoutingData)).start();
            new Thread(new AlertListener(algo, modelRoutingData, TYPE_MARKET)).start();

            if (modelRoutingData.execType.equals("Trade")) {
                new CalculatePositions(Repository.strategy.get(this.algo.nameAlgo), modelRoutingData);
            }

            if (algo.graphEnable) {
                Latency.latencyADR(algo, modelRoutingData);
            }

        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
        }

    }

}
