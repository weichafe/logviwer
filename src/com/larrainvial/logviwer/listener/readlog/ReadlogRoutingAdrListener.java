package com.larrainvial.logviwer.listener.readlog;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.readlog.ReadlogRoutingAdrEvent;
import com.larrainvial.logviwer.event.stringtofix.DolarEvent;
import com.larrainvial.logviwer.event.stringtofix.RoutingAdrEvent;
import com.larrainvial.logviwer.fxvo.Dialog;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Notifier;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

                String message = sc.nextLine();

                if (verifyMessageFix(message)){
                    Controller.dispatchEvent(new RoutingAdrEvent(algo, message));
                    algo.countRoutingAdr++;

                } else {
                    message = readFromFile(algo.countRoutingAdr);
                    Controller.dispatchEvent(new RoutingAdrEvent(algo, message));
                    algo.countRoutingAdr++;
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
            Helper.printerLog(e.getMessage().toString());
            Notifier.INSTANCE.notifyError("Error", e.getMessage().toString());
        }

    }

    private String readFromFile(int position) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(algo.fileRoutingAdr));

        String line = reader.readLine();
        List<String> lines = new ArrayList<String>();

        while (line != null) {
            lines.add(line);
            line = reader.readLine();
        }

        String message = lines.get(position);

        if (verifyMessageFix(message)){
            return message;

        } else {
            this.readFromFile(position);
        }

        return null;

    }




    public boolean verifyMessageFix(String message){

        if (message.indexOf("8=FIX.4.4") > -1 && message.indexOf("10=") > -1){
            return true;

        } else {
            return false;
        }

    }

}
