package com.larrainvial.logviwer;

import com.larrainvial.logviwer.model.ModelDolar;
import com.larrainvial.logviwer.utils.Helper;
import com.larrainvial.logviwer.utils.Latency;
import com.larrainvial.logviwer.utils.PropertiesFile;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;
import java.util.logging.Level;

public class Start extends Application {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public void start(Stage primaryStage) {

        try {

            Repository.logviewer  = new PropertiesFile(Repository.locationPath + "logviewer.properties");

            Latency.LATENCY_MIN = Integer.valueOf(Repository.logviewer.getPropertiesString("LATENCY_MIN"));
            Latency.LATENCY_MAX = Integer.valueOf(Repository.logviewer.getPropertiesString("LATENCY_MAX"));
            Latency.LATENCY_GRAPH = Integer.valueOf(Repository.logviewer.getPropertiesString("LATENCY_GRAPH"));

            ModelDolar.VARIACION_CLP = Double.valueOf(Repository.logviewer.getPropertiesString("VARIACION_CLP"));
            ModelDolar.VARIACION_CAD = Double.valueOf(Repository.logviewer.getPropertiesString("VARIACION_CAD"));
            ModelDolar.VARIACION_COFX = Double.valueOf(Repository.logviewer.getPropertiesString("VARIACION_COFX"));

            ModelDolar.CLP = Repository.logviewer.getPropertiesString("CLP");
            ModelDolar.CAD = Repository.logviewer.getPropertiesString("CAD");
            ModelDolar.COFX = Repository.logviewer.getPropertiesString("COFX");

            logger.info("Inicializando servidor... ");

            Repository.primaryStage = primaryStage;
            Repository.primaryStage.setTitle("Log Viewer");

            Repository.rootLayout_Loader.setLocation(Start.class.getResource("view/rootLayout.fxml"));
            BorderPane rootLayout_Loader = (BorderPane) Repository.rootLayout_Loader.load();

            Repository.principalTabPanel_Loader.setLocation(Start.class.getResource("view/PrincipalTabPanel.fxml"));
            Repository.tabPanePrincipalTabPanel = (TabPane) Repository.principalTabPanel_Loader.load();

            rootLayout_Loader.setCenter(Repository.tabPanePrincipalTabPanel);

            Repository.scene = new Scene(rootLayout_Loader);
            primaryStage.setScene(Repository.scene);
            primaryStage.show();

            Helper helper = new Helper();
            helper.createStrategy();


            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    logger.info("\nClose Aplications\n");
                    Repository.socketAcceptor.stop();
                    System.exit(0);
                }
            });


        } catch (Exception ex){
            logger.error(Level.SEVERE);
            ex.printStackTrace();
            System.exit(0);
        }

    }


}