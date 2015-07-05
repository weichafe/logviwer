package com.larrainvial.logviwer.listener.readlog;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.readlog.ReadLogRoutingLocalEvent;
import com.larrainvial.logviwer.event.stringtofix.RoutingLocalEvent;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

import java.util.Scanner;

public class ReadLogRoutingLocalListener implements Listener {

    public Algo algo;

    public ReadLogRoutingLocalListener(Algo algo){
        this.algo = algo;
    }

    @Override
    public synchronized void eventOccurred(Event event){

        try {

            ReadLogRoutingLocalEvent ev = (ReadLogRoutingLocalEvent) event;

            if(!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;

            Scanner sc = new Scanner(algo.inputStreamRoutingLocal, "UTF-8");

            while (sc.hasNextLine()) {
                Controller.dispatchEvent(new RoutingLocalEvent(algo, sc.nextLine()));
            }


        } catch (Exception e) {
            Helper.exception(e);
        }

    }

}
