package com.larrainvial.logviwer.listener.sendtoview;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.model.ModelPositions;
import com.larrainvial.logviwer.model.ModelRoutingData;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.logging.Level;

public class PositionViewListener implements Runnable {

    public Algo algo;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    public ModelRoutingData modelRoutingData;

    public PositionViewListener(Algo algo, ModelRoutingData modelRoutingData) {
        this.algo = algo;
        this.modelRoutingData = modelRoutingData;
    }


    @Override
    public synchronized void run() {

        try {

            if(!this.algo.nameAlgo.equals(algo.nameAlgo)) return;

            synchronized (algo.positionsMasterListHash) {

                for (Map.Entry<String, ModelPositions> e : algo.positionsMasterListHash.entrySet()) {

                    ModelPositions modelPositions = algo.positionsMasterListHash.get(e.getKey());

                    if (!algo.positions.containsKey(e.getKey())) {

                        algo.panelPositionsTableView.getItems().add(algo.countPositions, modelPositions);

                        algo.positions.put(e.getKey(), algo.countPositions);
                        algo.countPositions++;

                    } else {

                        algo.panelPositionsTableView.getItems().set(algo.positions.get(e.getKey()), modelPositions);

                    }

                }

            }

        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
        }
  }

}



