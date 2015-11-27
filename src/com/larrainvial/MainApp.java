package com.larrainvial;


import com.larrainvial.logviwer.Start;
import com.larrainvial.logviwer.Repository;
import com.larrainvial.sellside.MainSellSide;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class MainApp extends Application {

    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static final Logger logger = Logger.getLogger(MainApp.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception {

        //LogViwer
        new Start().start(primaryStage);

        //SellSide
        new MainSellSide().start();

    }

    public static void main(String[] args) {

        if (isWindows()) {
            Repository.locationPath = "C:\\Program Files (x86)\\LarrainVial\\Logviewer\\Resources\\";
            PropertyConfigurator.configure(Repository.locationPath + "log4j.properties");
            logger.info("Server is Windows");
            logger.info("LocalPath " + Repository.locationPath);

        } else if (isMac()) {
            logger.info("Server is Mac");

        } else if (isUnix()) {
            Repository.locationPath = "/opt/Logviewer/Resources/";
            PropertyConfigurator.configure(Repository.locationPath + "log4j.properties");
            logger.info("Server is Unix or Linux");
            logger.info("LocalPath " + Repository.locationPath);
        }

        launch(args);

    }


    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
    }

}
