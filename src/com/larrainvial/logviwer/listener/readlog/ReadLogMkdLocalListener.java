package com.larrainvial.logviwer.listener.readlog;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.listener.stringtofix.MarketDataLocalListener;
import org.apache.log4j.Logger;

import java.util.Scanner;
import java.util.logging.Level;

public class ReadLogMkdLocalListener implements Runnable {

    public Algo algo;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public ReadLogMkdLocalListener(Algo algo){
        this.algo = algo;
    }

    @Override
    public synchronized void run(){

        try {

            Scanner sc = new Scanner(algo.inputStreamMkdLocal, "UTF-8");

            while (sc.hasNextLine()) {

                String message = sc.nextLine();

                if (verifyMessageFix(message)){
                    new Thread(new MarketDataLocalListener(algo, message)).start();
                    algo.countMkdLocal++;

                }

            }


        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
        }

    }


    public boolean verifyMessageFix(String message){

        if (message.indexOf("8=FIX.4.4") > -1 && message.indexOf("10=") > -1){
            return true;

        } else {
            return false;
        }

    }



}
