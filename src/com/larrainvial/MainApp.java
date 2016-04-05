package com.larrainvial;


import com.larrainvial.logviwer.Start;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.sellside.MainSellSide;
import javafx.application.Application;
import javafx.event.Event;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.util.logging.Level;

public class MainApp extends Application {

    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static final Logger logger = Logger.getLogger(MainApp.class.getName());

    public static void main(String[] args) {

        try {

            if (isWindows()) {
                Repository.locationPath = "C:\\Program Files (x86)\\LarrainVial\\Logviewer\\Resources\\";
                PropertyConfigurator.configure(Repository.locationPath + "log4jWindows.properties");
                logger.info("Server is Windows");
                logger.info("LocalPath " + Repository.locationPath);
                Repository.nameFileQuickFix = "windows.ini";

            } else if (isMac()) {
                logger.info("Server is Mac");

            } else if (isUnix()) {
                Repository.locationPath = "/opt/Logviewer/Resources/";
                PropertyConfigurator.configure(Repository.locationPath + "log4jLinux.properties");
                logger.info("Server is Unix or Linux");
                logger.info("LocalPath " + Repository.locationPath);
                Repository.nameFileQuickFix = "linux.ini";
                verifyDirectory();
            }

            launch(args);

        } catch (Exception ex){
            ex.printStackTrace();
        }

    }



    @Override
    public void start(Stage primaryStage) throws Exception {

        //Todo: Inicia logviewer
        new Start().start(primaryStage);

        //Todo: Inicia Sell Side
        //new MainSellSide().start();

    }


    public static boolean isWindows() throws Exception {
        return (OS.indexOf("win") >= 0);
    }

    public static boolean isMac() throws Exception {
        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isUnix() throws Exception {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
    }

    public static void verifyDirectory() throws Exception {

        try {

            String sDirectorio = "/root/opt/Logviewer/Output/";
            File f = new File(sDirectorio);

            if (!f.exists()){
                f.mkdirs();
                System.out.println("se creo /root/opt/Logviewer/Output/");
                System.out.println("se creo /root/opt/Logviewer/Output/");
            } else {
                System.out.println("la ruta /root/opt/Logviewer/Output/ Exite! ");
            }

            sDirectorio = "/root/opt/Logviewer/Output/log/";
            f = new File(sDirectorio);

            if (!f.exists()){
                f.mkdirs();
                System.out.println("se creo /root/opt/Logviewer/log/");
            } else {
                System.out.println("la ruta /root/opt/Logviewer/log/ Exite! ");
            }

            sDirectorio = "/root/opt/Logviewer/Output/store/";
            f = new File(sDirectorio);

            if (!f.exists()){
                f.mkdirs();
                System.out.println("se creo /root/opt/Logviewer/store/");
            } else {
                System.out.println("la ruta /root/opt/Logviewer/store/ Exite! ");
            }

            sDirectorio = "/root/opt/Logviewer/Output/console/";
            f = new File(sDirectorio);

            if (!f.exists()){
                f.mkdirs();
                System.out.println("se creo /root/opt/Logviewer/console/");
            } else {
                System.out.println("la ruta /root/opt/Logviewer/console/ Exite! ");
            }


        } catch (Exception ex) {
           ex.printStackTrace();
           logger.error(Level.SEVERE, ex);
        }



    }

}
