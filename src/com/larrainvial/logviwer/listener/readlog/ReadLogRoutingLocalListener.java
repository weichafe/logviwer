package com.larrainvial.logviwer.listener.readlog;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.listener.stringtofix.RoutingLocalListener;
import org.apache.log4j.Logger;

import java.util.Scanner;
import java.util.logging.Level;

public class ReadLogRoutingLocalListener extends Thread {

    public Algo algo;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public ReadLogRoutingLocalListener(Algo algo){
        this.algo = algo;
    }

    @Override
    public synchronized void run(){

        try {

            if (!algo.nameAlgo.equals(algo.nameAlgo)) return;

            Scanner sc = new Scanner(algo.inputStreamRoutingLocal, "UTF-8");

            while (sc.hasNextLine()) {

                String message = sc.nextLine();

                if (verifyMessageFix(message)) {

                    RoutingLocalListener routingLocalListener = new RoutingLocalListener(algo,message);
                    routingLocalListener.start();
                    algo.countRoutingLocal++;
                }
            }

            algo.blockRoutingLocal = true;


        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
        }

    }


    public boolean verifyMessageFix(String message){
        return (message.indexOf("8=FIX.4.4") > -1 && message.indexOf("10=") > -1) ? true : false;
    }

}
