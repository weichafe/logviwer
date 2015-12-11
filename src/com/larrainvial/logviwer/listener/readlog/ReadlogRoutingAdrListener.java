package com.larrainvial.logviwer.listener.readlog;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.listener.stringtofix.RoutingADRListener;
import org.apache.log4j.Logger;

import java.util.Scanner;
import java.util.logging.Level;

public class ReadlogRoutingAdrListener extends Thread {

    public Algo algo;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public ReadlogRoutingAdrListener(Algo algo){
        this.algo = algo;
    }

    @Override
    public synchronized void run(){

        try {

            if(!algo.nameAlgo.equals(algo.nameAlgo)) return;

            Scanner sc = new Scanner(algo.inputStreamRoutingAdr, "UTF-8");

            while (sc.hasNextLine()) {

                String message = sc.nextLine();

                if (verifyMessageFix(message)){

                    RoutingADRListener routingAdrListener =  new RoutingADRListener(algo, message);
                    routingAdrListener.start();
                    algo.countRoutingAdr++;
                }
            }

            algo.blockRoutingADR = true;

        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
        }

    }


    public boolean verifyMessageFix(String message){
        return (message.indexOf("8=FIX.4.4") > -1 && message.indexOf("10=") > -1) ? true : false;
    }

}
