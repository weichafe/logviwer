package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.ReadLogEvent;
import com.larrainvial.logviwer.event.TriggerReadFileEvent;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

public class TriggerReadFileListener implements Listener {

    private Algo algo;

    @Override
    public void eventOccurred(Event event) {

        try {

            TriggerReadFileEvent ev = (TriggerReadFileEvent) event;

            algo = Repository.strategy.get(ev.nameAlgo);

            if (algo.isMkd_dolar_toggle()) {
                Controller.dispatchEvent(new ReadLogEvent(this, ev.nameAlgo, ev.typeMarket.get(0), ev.typeFile.get(0)));
            }

            if (algo.isMkd_local_toggle()) {
                Controller.dispatchEvent(new ReadLogEvent(this, ev.nameAlgo, ev.typeMarket.get(1), ev.typeFile.get(1)));
            }

            if (algo.isMkd_adr_toggle()) {
                Controller.dispatchEvent(new ReadLogEvent(this, ev.nameAlgo, ev.typeMarket.get(2), ev.typeFile.get(2)));
            }

            if (algo.isRouting_local_toggle()) {
                Controller.dispatchEvent(new ReadLogEvent(this, ev.nameAlgo, ev.typeMarket.get(3), ev.typeFile.get(3)));
            }

            if (algo.isRouting_adr_toggle()) {
                Controller.dispatchEvent(new ReadLogEvent(this, ev.nameAlgo, ev.typeMarket.get(4), ev.typeFile.get(4)));
            }

        } catch (Exception e){
            Helper.exception(e);
        }

    }
}
