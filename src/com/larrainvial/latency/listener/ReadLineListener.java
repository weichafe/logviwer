package com.larrainvial.latency.listener;

import com.larrainvial.latency.Algo;
import com.larrainvial.latency.Repository;
import com.larrainvial.latency.event.GetStringEvent;
import com.larrainvial.latency.event.ReadLineEvent;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

import java.util.Scanner;


public class ReadLineListener implements Listener {

    public Algo algo;

    public ReadLineListener(Algo algo){
        this.algo = algo;
    }

    @Override
    public synchronized void eventOccurred(Event event){

        try {

            ReadLineEvent ev = (ReadLineEvent) event;

            if(!ev.algo.nameFile.equals(algo.nameFile)) return;

            Repository.directoryFile.put(ev.algo.nameFile, ev.algo.nameFile);

            Scanner sc = new Scanner(ev.algo.fileStreamHashMap.get(ev.algo.nameDirectory + "\\" + ev.algo.nameFile), "UTF-8");

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Controller.dispatchEvent(new GetStringEvent(ev.algo, line));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
