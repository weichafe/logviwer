package com.larrainvial.logviwer;

import com.larrainvial.logviwer.utils.Control;
import com.larrainvial.logviwer.utils.Helper;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class MainLogViwer extends Application {


    public void start(Stage primaryStage) {

        try {

            System.out.print("Inicializando servidor... ");

            Repository.primaryStage = primaryStage;
            Repository.primaryStage.setTitle("Log Viwer");

            Repository.rootLayout_Loader.setLocation(MainLogViwer.class.getResource("view/rootLayout.fxml"));
            BorderPane rootLayout_Loader = (BorderPane) Repository.rootLayout_Loader.load();

            Repository.principalTabPanel_Loader.setLocation(MainLogViwer.class.getResource("view/PrincipalTabPanel.fxml"));
            Repository.tabPanePrincipalTabPanel = (TabPane) Repository.principalTabPanel_Loader.load();
            rootLayout_Loader.setCenter(Repository.tabPanePrincipalTabPanel);

            Repository.scene = new Scene(rootLayout_Loader);
            primaryStage.setScene(Repository.scene);

            primaryStage.show();

            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    System.exit(0);
                }
            });

            Control.initialize();
            Helper.createStrategy();

        } catch (Exception e){
            Helper.exception(e);
            System.exit(0);
        }



    }

}