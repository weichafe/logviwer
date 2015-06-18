package com.larrainvial.logviwer.listener.readlog;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.logviwer.event.readlog.ReadLogMkdLocalEvent;
import com.larrainvial.logviwer.event.stringtofix.MarketDataLocalEvent;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

import java.util.Scanner;

public class ReadLogMkdLocalListener implements Listener {

    private Algo algo;

    @Override
    public void eventOccurred(Event event){

        try {

            ReadLogMkdLocalEvent ev = (ReadLogMkdLocalEvent) event;

            algo = Repository.strategy.get(ev.nameAlgo);

            Scanner sc = new Scanner(ev.inputStream, "UTF-8");

            while (sc.hasNextLine()) {
                Controller.dispatchEvent(new MarketDataLocalEvent(this, sc.nextLine(), ev.nameAlgo, ev.typeMarket));
            }


        } catch (Exception e) {
            Helper.exception(e);
        }

    }

}
