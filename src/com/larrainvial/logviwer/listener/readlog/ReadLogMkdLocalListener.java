package com.larrainvial.logviwer.listener.readlog;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.readlog.ReadLogMkdLocalEvent;
import com.larrainvial.logviwer.event.stringtofix.MarketDataLocalEvent;
import com.larrainvial.logviwer.fxvo.Dialog;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import org.apache.log4j.Logger;

import java.util.Scanner;

public class ReadLogMkdLocalListener implements Listener {

    public Algo algo;

    public ReadLogMkdLocalListener(Algo algo){
        this.algo = algo;
    }

    @Override
    public synchronized void eventOccurred(Event event){

        try {

            ReadLogMkdLocalEvent ev = (ReadLogMkdLocalEvent) event;

            if(!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;

            Scanner sc = new Scanner(algo.inputStreamMkdLocal, "UTF-8");

            while (sc.hasNextLine()) {
                Controller.dispatchEvent(new MarketDataLocalEvent(algo, sc.nextLine()));
            }


        } catch (Exception e) {
            e.printStackTrace();
            Helper.printerLog(e.getMessage().toString());
            Notifier.INSTANCE.notifyError("Error", e.getMessage().toString());
        }

    }


}
