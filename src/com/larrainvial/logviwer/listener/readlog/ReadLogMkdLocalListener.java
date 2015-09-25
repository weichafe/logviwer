package com.larrainvial.logviwer.listener.readlog;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.readlog.ReadLogMkdLocalEvent;
import com.larrainvial.logviwer.event.stringtofix.DolarEvent;
import com.larrainvial.logviwer.event.stringtofix.MarketDataLocalEvent;
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

public class ReadLogMkdLocalListener implements Listener {

    public Algo algo;
    private static Logger logger = Logger.getLogger(ReadLogMkdLocalListener.class.getName());

    public ReadLogMkdLocalListener(Algo algo){
        this.algo = algo;
    }

    @Override
    public synchronized void eventOccurred(Event event){

        try {

            ReadLogMkdLocalEvent ev = (ReadLogMkdLocalEvent) event;

            if(!ev.algo.nameAlgo.equals(algo.nameAlgo)) return;

            Scanner sc = new Scanner(algo.inputStreamMkdLocal, "UTF-8");

            while (sc.hasNextLine()) {

                String message = sc.nextLine();

                if (verifyMessageFix(message)){
                    Controller.dispatchEvent(new MarketDataLocalEvent(algo, message));
                    algo.countMkdLocal++;

                } else {
                    message = readFromFile(algo.countMkdLocal);
                    Controller.dispatchEvent(new MarketDataLocalEvent(algo, message));
                    algo.countMkdLocal++;
                }


            }


        } catch (Exception e) {
            logger.error(e);
        }

    }


    private String readFromFile(int position) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(algo.fileMkdLocal));

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
