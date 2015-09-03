package com.larrainvial.logviwer.listener.sendtoview;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.sendtoview.PositionViewEvent;
import com.larrainvial.logviwer.model.ModelPositions;
import com.larrainvial.logviwer.fxvo.Dialog;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import eu.hansolo.enzo.notification.Notification;
import javafx.application.Platform;
import org.apache.log4j.Logger;

import java.util.Map;

public class PositionViewListener implements Listener {

    public Algo algo;

    public PositionViewListener(Algo algo) {
        this.algo = algo;
    }


    @Override
    public synchronized void eventOccurred(Event event) {

        try {

            PositionViewEvent ev = (PositionViewEvent) event;

            if(!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;

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

        } catch (Exception e) {
            e.printStackTrace();
            Helper.printerLog(e.getMessage().toString());
            Notifier.INSTANCE.notifyError("Error", e.getMessage().toString());
        }
  }

}



