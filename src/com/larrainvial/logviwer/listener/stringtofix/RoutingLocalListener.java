package com.larrainvial.logviwer.listener.stringtofix;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.listener.alert.AlertListener;
import com.larrainvial.logviwer.listener.alert.AlertMarketMakerBCSListener;
import com.larrainvial.logviwer.listener.calculate.CalculateMMBCSListener;
import com.larrainvial.logviwer.listener.sendtoview.PositionViewListener;
import com.larrainvial.logviwer.model.ModelRoutingData;
import com.larrainvial.logviwer.utils.CalculatePositions;
import com.larrainvial.logviwer.utils.Constants;
import com.larrainvial.logviwer.utils.Latency;
import com.larrainvial.logviwer.utils.StringToRoutingData;
import org.apache.log4j.Logger;
import java.util.logging.Level;

public class RoutingLocalListener extends Thread {

    public Algo algo;
    public String message;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public RoutingLocalListener(Algo algo, String message) {
        this.algo = algo;
        this.message = message;
    }


    @Override
    public synchronized void run(){

        try {

            if (message.equals(Constants.EMPTY)) return;

            StringToRoutingData stringToRoutingData = new StringToRoutingData();
            ModelRoutingData modelRoutingData = stringToRoutingData.routing(message);

            AlertListener alertListener = new AlertListener(algo, modelRoutingData, Constants.TypeMarket.ROUTING_LOCAL);
            alertListener.start();

            AlertMarketMakerBCSListener alertMarketMakerBCSListener = new AlertMarketMakerBCSListener(algo, modelRoutingData, Constants.TypeMarket.ROUTING_LOCAL);
            alertMarketMakerBCSListener.wait();

            if (algo.graphEnable) Latency.latencyLocal(algo, modelRoutingData);

            if (algo.nameAlgo.equals(Constants.MarketMakerBCS.NAME)){
                new CalculateMMBCSListener(algo, modelRoutingData);
                new PositionViewListener(algo, modelRoutingData).start();

            } else {
                new CalculatePositions(algo, modelRoutingData);
                new PositionViewListener(algo, modelRoutingData).start();
            }



        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
        }

    }

}
