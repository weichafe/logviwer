package com.larrainvial.latency;

import com.larrainvial.latency.event.GetDirectoryEvent;
import com.larrainvial.latency.event.GetStringEvent;
import com.larrainvial.latency.event.ReadLineEvent;
import com.larrainvial.latency.listener.GetDirectoryListener;
import com.larrainvial.latency.listener.GetStringListener;
import com.larrainvial.latency.listener.ReadLineListener;
import com.larrainvial.latency.vo.LatencyVO;
import com.larrainvial.trading.emp.Controller;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Algo {

    public String nameDirectory;
    public String nameFile;
    public String typeEngine;
    public String fechaDesde;
    public String fechaHasta;
    public String ruteDirectory;
    public HashMap<String, FileInputStream> fileStreamHashMap = new HashMap<String, FileInputStream>();

    public HashMap<String, String> allFileDirectory = new HashMap<String, String>();
    public HashMap<String, LatencyVO> hashLatencyVO = new HashMap<String, LatencyVO>();

    public ReadLineListener readLineListener;
    public GetStringListener getStringListener;
    public GetDirectoryListener getDirectoryListener;

    public Algo(String nameDirectory,String typeEngine, String fechaDesde, String fechaHasta) {

        try {

            if (nameDirectory!=null && !nameDirectory.trim().equals("")) {
                this.nameDirectory = nameDirectory;
                this.typeEngine = typeEngine;
                this.ruteDirectory = nameDirectory;
                this.fechaHasta = fechaHasta;
                this.fechaDesde = fechaDesde;

                readLineListener = new ReadLineListener(this);
                getStringListener = new GetStringListener(this);
                getDirectoryListener = new GetDirectoryListener(this);

                Controller.addEventListener(GetDirectoryEvent.class, getDirectoryListener);
                Controller.addEventListener(GetStringEvent.class, getStringListener);
                Controller.addEventListener(ReadLineEvent.class, readLineListener);

                Repository.strategy.put(this.nameDirectory, this);

                this.start(this);
            }

        } catch (Exception e){
            System.out.print(e.getMessage());
        }
    }


    public void start(final Algo algo) throws Exception {


        TimerTask timerTask = new TimerTask(){

            public void run() {
                Controller.dispatchEvent(new GetDirectoryEvent(algo));
            }

        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 1, 100);

    }




}