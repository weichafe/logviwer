package com.larrainvial.logviwer.listener.readlog;

import com.larrainvial.logviwer.Algo;
import com.larrainvial.logviwer.listener.stringtofix.DolarListener;
import org.apache.log4j.Logger;

import java.util.Scanner;
import java.util.logging.Level;

public class ReadFromDolarListener extends Thread {

    public Algo algo;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public ReadFromDolarListener(Algo algo){
        this.algo = algo;
    }


    @Override
    public synchronized void run(){

        try {

            if (!algo.nameAlgo.equals(algo.nameAlgo)) return;

            long time_start, time_end;
            time_start = System.currentTimeMillis();

            Scanner sc = new Scanner(algo.inputStreamDolar, "UTF-8");

            while (sc.hasNextLine()) {

                String message = sc.nextLine();

                if (verifyMessageFix(message)) {
                    DolarListener dolarListener = new DolarListener(algo, message);
                    dolarListener.start();
                    algo.countDolar++;
                }
            }

            algo.blockDolar = true;

            time_end = System.currentTimeMillis();
            System.out.println("the task has taken inputStreamDolar " + algo.nameAlgo + " " + ( time_end - time_start ) +" milliseconds");

        } catch (Exception ex) {
            logger.error(Level.SEVERE, ex);
            ex.printStackTrace();
        }

    }


    public boolean verifyMessageFix(String message){
        return (message.indexOf("8=FIX.4.4") > -1 && message.indexOf("10=") > -1) ? true : false;
    }


}

