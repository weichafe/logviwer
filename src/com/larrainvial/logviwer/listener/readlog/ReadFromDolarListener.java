package com.larrainvial.logviwer.listener.readlog;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.readlog.ReadFromDolarEvent;
import com.larrainvial.logviwer.event.stringtofix.DolarEvent;
import com.larrainvial.logviwer.fxvo.Dialog;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

import java.util.Scanner;

public class ReadFromDolarListener implements Listener {

    public Algo algo;

    public ReadFromDolarListener(Algo algo){
        this.algo = algo;
    }

    @Override
    public synchronized void eventOccurred(Event event){

        try {

            ReadFromDolarEvent ev = (ReadFromDolarEvent) event;

            if(!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;

            Scanner sc = new Scanner(algo.inputStreamMkdDolar, "UTF-8");

            while (sc.hasNextLine()) {
                Controller.dispatchEvent(new DolarEvent(algo, sc.nextLine()));
            }


        } catch (Exception e) {
            Dialog.exception(e);
        }

    }

}
