package com.larrainvial.logviwer.listener;


import com.larrainvial.logviwer.event.ReadLogEvent;
import com.larrainvial.logviwer.event.StringToFixMessageEvent;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

import java.util.Scanner;

public class ReadLogListener implements Listener {


    @Override
    public void eventOccurred(Event event){

        try {

            ReadLogEvent ev = (ReadLogEvent) event;

            System.out.println("read LOG " + ev.nameAlgo + " " + ev.typeMarket);

            Scanner sc = new Scanner(ev.inputStream, "UTF-8");

            while (sc.hasNextLine()) {
                Controller.dispatchEvent(new StringToFixMessageEvent(this, sc.nextLine(), ev.nameAlgo, ev.typeMarket));
            }



        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
