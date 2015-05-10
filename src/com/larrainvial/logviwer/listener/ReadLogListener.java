package com.larrainvial.logviwer.listener;


import com.larrainvial.logviwer.event.ReadLogEvent;
import com.larrainvial.logviwer.event.StringToFixMessageEvent;
import com.larrainvial.trading.emp.Controller;
import com.larrainvial.trading.emp.Event;
import com.larrainvial.trading.emp.Listener;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class ReadLogListener implements Listener {


    @Override
    public void eventOccurred(Event event){

        try {

            ReadLogEvent ev = (ReadLogEvent) event;

            this.scannerRead(ev.nameAlgo, ev.typeMarket, ev.inputStream);
            //this.byteBufferRead(ev.nameAlgo, ev.typeMarket, ev.inputStream);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void scannerRead(String nameAlgo, String typeMarket, FileInputStream inputStream){

        long startTime =  System.nanoTime();

        Scanner sc = new Scanner(inputStream, "UTF-8");

        while (sc.hasNextLine()) {
            Controller.dispatchEvent(new StringToFixMessageEvent(this, sc.nextLine(), nameAlgo, typeMarket));
        }

        if  (sc.ioException()  !=  null ) {
            sc.ioException().printStackTrace();
        }

        long endTime = System.nanoTime();
        long elapsedTimeInMillis = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);

        System.out.println("Total elapsed time: " + elapsedTimeInMillis + " ms"  );

    }


    public void byteBufferRead (String nameAlgo, String typeMarket, FileInputStream inputStream){

        long startTime = System.nanoTime();
        Path file = Paths.get("log\\FIX.4.4-ALGOARBADR5-MDFHBLP.messages_20150508.log");

        try {

            Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8);

            for( String line : (Iterable<String>) lines::iterator ) {
               Controller.dispatchEvent(new StringToFixMessageEvent(this, line, nameAlgo, typeMarket));
            }

        } catch (IOException ioe){
            ioe.printStackTrace();
        }

        long endTime = System.nanoTime();
        long elapsedTimeInMillis = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
        System.out.println("Total elapsed time: " + elapsedTimeInMillis + " ms");
    }

}
