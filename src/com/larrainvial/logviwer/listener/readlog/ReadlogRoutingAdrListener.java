package com.larrainvial.logviwer.listener.readlog;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.readlog.ReadlogRoutingAdrEvent;
import com.larrainvial.logviwer.event.stringtofix.RoutingAdrEvent;
import com.larrainvial.logviwer.fxvo.Dialog;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import org.apache.log4j.Logger;

import java.util.Scanner;

public class ReadlogRoutingAdrListener implements Listener {

    public Algo algo;

    public ReadlogRoutingAdrListener(Algo algo){
        this.algo = algo;
    }


    @Override
    public synchronized void eventOccurred(Event event){

        try {

            ReadlogRoutingAdrEvent ev = (ReadlogRoutingAdrEvent) event;

            if(!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;

            Scanner sc = new Scanner(algo.inputStreamRoutingAdr, "UTF-8");

            while (sc.hasNextLine()) {
                Controller.dispatchEvent(new RoutingAdrEvent(algo, sc.nextLine()));
            }





        } catch (Exception e) {
            e.printStackTrace();
            Helper.printerLog(e.getMessage().toString());
            Notifier.INSTANCE.notifyError("Error", e.getMessage().toString());
        }

    }

}
