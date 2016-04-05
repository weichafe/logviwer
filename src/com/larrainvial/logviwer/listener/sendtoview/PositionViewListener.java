package com.larrainvial.logviwer.listener.sendtoview;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.sendtoview.PositionViewEvent;
import com.larrainvial.logviwer.model.ModelMMBCS;
import com.larrainvial.logviwer.model.ModelPositions;
import com.larrainvial.logviwer.utils.Constants;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.logging.Level;

public class PositionViewListener implements Listener {

    public Algo algo;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public PositionViewListener(Algo algo) {
        this.algo = algo;
    }


    @Override
    public synchronized void eventOccurred(Event event) {

        try {

            PositionViewEvent ev = (PositionViewEvent) event;

            if (!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;

            if (ev.algo.nameAlgo.equals(Constants.MarketMakerBCS.NAME)){

                synchronized (algo.mmBCSMasterListHash) {

                    for (Map.Entry<String, ModelMMBCS> e : algo.mmBCSMasterListHash.entrySet()) {

                        ModelMMBCS modelMMBCS = algo.mmBCSMasterListHash.get(e.getKey());

                        if (!algo.mmBCE.containsKey(e.getKey())) {

                            algo.panelMMBCSTableView.getItems().add(algo.countMMBCC, modelMMBCS);

                            algo.mmBCE.put(e.getKey(), algo.countMMBCC);
                            algo.countMMBCC++;

                        } else {
                            algo.panelMMBCSTableView.getItems().set(algo.mmBCE.get(e.getKey()), modelMMBCS);
                        }

                    }
                }


            } else {

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
            }

        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
            logger.error(algo.nameAlgo);
        }

    }



}



