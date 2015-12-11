package com.larrainvial.logviwer.listener.stringtofix;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.listener.alert.AlertListener;
import com.larrainvial.logviwer.listener.sendtoview.PositionViewListener;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.CalculatePositions;
import com.larrainvial.logviwer.utils.Constants;
import com.larrainvial.logviwer.utils.Latency;
import com.larrainvial.logviwer.utils.StringToRoutingData;
import org.apache.log4j.Logger;

import java.util.logging.Level;

public class RoutingADRListener extends Thread {

    public Algo algo;
    public String message;
    private Logger logger = Logger.getLogger(this.getClass().getName());


    public RoutingADRListener(Algo algo, String message) {
        this.algo = algo;
        this.message = message;
    }

    @Override
    public synchronized void run(){

        try {

            if (message.equals(Constants.EMPTY)) return;

            StringToRoutingData stringToRoutingData = new StringToRoutingData();
            ModelRoutingData modelRoutingData = stringToRoutingData.routing(message);

            AlertListener alertListener = new AlertListener(algo, modelRoutingData, Constants.TypeMarket.ROUTING_ADR);
            alertListener.start();

            PositionViewListener positionViewListener = new PositionViewListener(algo, modelRoutingData);
            positionViewListener.start();

            new CalculatePositions(Repository.strategy.get(algo.nameAlgo), modelRoutingData);

            if (algo.graphEnable) {
                Latency.latencyADR(algo, modelRoutingData);
            }

        } catch (Exception ex){
            logger.error(Level.SEVERE, ex);
        }

    }

}
