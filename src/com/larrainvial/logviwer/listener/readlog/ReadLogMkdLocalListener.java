package com.larrainvial.logviwer.listener.readlog;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.event.readlog.ReadLogMkdLocalEvent;
import com.larrainvial.logviwer.event.stringtofix.MarketDataLocalEvent;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;
import org.apache.log4j.Logger;

import java.util.Scanner;
import java.util.logging.Level;

public class ReadLogMkdLocalListener implements Listener {

    public Algo algo;
    private Logger logger = Logger.getLogger(this.getClass().getName());


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

                }
            }

            algo.validateMKDLocal = true;

            if (algo.modelXml.remoteFile == true) {
                ev.algo.blokedMkdLocal = false;
                algo.mainCopyFile.copyMkdLocalFile();
            }


        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
        }

    }

    public boolean verifyMessageFix(String message){
        return (message.indexOf("8=FIX.4.4") > -1 && message.indexOf("10=") > -1) ? true : false;

    }



}
