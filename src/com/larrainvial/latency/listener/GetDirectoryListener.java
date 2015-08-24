package com.larrainvial.latency.listener;

import com.larrainvial.latency.Algo;
import com.larrainvial.latency.event.GetDirectoryEvent;
import com.larrainvial.latency.event.ReadLineEvent;
import com.larrainvial.latency.utils.Helper;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import java.io.File;
import java.io.FileInputStream;


public class GetDirectoryListener implements Listener {

    public Algo algo;

    public GetDirectoryListener(Algo algo){
        this.algo = algo;
    }

    @Override
    public synchronized void eventOccurred(Event event){

        try {

            GetDirectoryEvent ev = (GetDirectoryEvent) event;

            if (!ev.algo.nameDirectory.equals(algo.nameDirectory)) return;

                File folder = new File(ev.algo.ruteDirectory);
                File[] listOfFiles = folder.listFiles();

                if (listOfFiles == null) return;

                for (final File fileEntry : listOfFiles) {

                    if (fileEntry.getName().indexOf("events") > -1) continue;

                    if (!fileEntry.isDirectory()) {

                        if (fileEntry.lastModified() >= Helper.getTransactionTime(ev.algo.fechaDesde) && fileEntry.lastModified() <= Helper.getTransactionTime(ev.algo.fechaHasta)) {

                            ev.algo.nameFile = fileEntry.getName();

                            if (!ev.algo.fileStreamHashMap.containsKey(ev.algo.nameDirectory + "\\" + ev.algo.nameFile)) {
                                ev.algo.fileStreamHashMap.put(ev.algo.nameDirectory + "\\" + ev.algo.nameFile, new FileInputStream(new File(ev.algo.nameDirectory + "\\" + ev.algo.nameFile)));
                            }

                            algo.allFileDirectory.put(fileEntry.getName(), ev.algo.ruteDirectory);
                            Controller.dispatchEvent(new ReadLineEvent(ev.algo, ev.algo.nameFile));
                        }
                    }

                }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
