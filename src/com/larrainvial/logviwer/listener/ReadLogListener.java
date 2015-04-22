package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.event.ReadLogEvent;
import com.larrainvial.logviwer.event.StringToFixMessageEvent;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Timer;
import java.util.TimerTask;


public class ReadLogListener implements Listener {

    private long

    @Override
    public void eventOccurred(Event event){

        try {

            ReadLogEvent ev = (ReadLogEvent) event;
            this.readlogDolar(ev.namefile, ev.nameAlgo , ev.typeMarket);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void readlogDolar(String namefile, String nameAlgo , String typeMarket) {

        try {


            File file = new File(namefile);
            FileReader f = new FileReader(file);
            BufferedReader b = new BufferedReader(f);

            TimerTask timerTask = new TimerTask(){

                public void run() {
                    String lineFromLog;
                    while ((lineFromLog = b.readLine()) != null){
                        Controller.dispatchEvent(new StringToFixMessageEvent(this, lineFromLog, nameAlgo, typeMarket));
                    }
                }
            };


            Timer timer = new Timer();
            timer.scheduleAtFixedRate(timerTask, 0, 1000);




        }catch (Exception e) {
            e.printStackTrace();
        }


    }
}
