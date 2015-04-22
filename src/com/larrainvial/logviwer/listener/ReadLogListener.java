package com.larrainvial.logviwer.listener;

import com.larrainvial.logviwer.Repository;
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

    private long lastModified;
    private TimerTask timerTask;


    @Override
    public void eventOccurred(Event event){

        try {

            ReadLogEvent ev = (ReadLogEvent) event;
            this.readlogDolar(ev.namefile, ev.nameAlgo , ev.typeMarket);
            lastModified = 0;

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void readlogDolar(String namefile, String nameAlgo , String typeMarket) {

        try {

            File archivo = new File(namefile);
            FileReader f = new FileReader(archivo);
            BufferedReader b = new BufferedReader(f);
            int timer_initial = Repository.hashMap_timeSlider.get(nameAlgo).intValue();
            stopTimer();

            timerTask = new TimerTask(){


                public void run() {

                    if(lastModified != archivo.lastModified()) {

                        lastModified = archivo.lastModified();

                        try {

                            String lineFromLog;

                            while ((lineFromLog = b.readLine()) != null) {
                                Controller.dispatchEvent(new StringToFixMessageEvent(this, lineFromLog, nameAlgo, typeMarket));
                            }

                            if(timer_initial != Repository.hashMap_timeSlider.get(nameAlgo).intValue()){
                                readlogDolar(namefile, nameAlgo, typeMarket);
                            }

                        }catch (Exception e) {
                           e.printStackTrace();
                        }

                    }

                }
            };

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(timerTask, 1, Repository.hashMap_timeSlider.get(nameAlgo).intValue() * 1000);



        }catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void stopTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }
}
