package com.larrainvial.process.Thread;

import com.larrainvial.logviwer.utils.Helper;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.apache.log4j.Logger;

import java.io.BufferedReader;


public class PrintConsole implements Runnable {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    public String text = "Console\n";
    public BufferedReader br;
    public TextArea textArea;
    public boolean print;

    public PrintConsole(BufferedReader br, TextArea textArea, boolean print) {

        this.br = br;
        this.textArea = textArea;
        this.print = print;
    }


    @Override
    public void run() {

        try {

            String line;

            while((line = br.readLine())!= null) {
                printConsole(textArea, print, line);
                System.out.println(line);
            }


        } catch (Exception ex){
            ex.printStackTrace();
        }

    }



    public void printConsole(TextArea textArea, boolean print, String line) throws Exception {

        try {

            if (!print) return;

            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    text += line + "\n";
                    textArea.setText(text);
                    logger.info(text);
                }

            });

        } catch (Exception ex){
            ex.printStackTrace();
            Helper.printerLog(ex.toString());
        }

    }
}
