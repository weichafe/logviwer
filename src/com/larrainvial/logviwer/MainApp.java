package com.larrainvial.logviwer;

import com.larrainvial.logviwer.utils.Control;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {

        Repository.primaryStage = primaryStage;
        Repository.primaryStage.setTitle("Log Viwer");

        Repository.rootLayout_Loader.setLocation(MainApp.class.getResource("view/rootLayout.fxml"));
        BorderPane rootLayout_Loader = (BorderPane) Repository.rootLayout_Loader.load();

        Repository.principalTabPanel_Loader.setLocation(MainApp.class.getResource("view/PrincipalTabPanel.fxml"));
        Repository.tabPanePrincipalTabPanel = (TabPane) Repository.principalTabPanel_Loader.load();

        rootLayout_Loader.setCenter(Repository.tabPanePrincipalTabPanel);


        Scene scene = new Scene(rootLayout_Loader);
        primaryStage.setScene(scene);
        primaryStage.show();

        Control.initialize();
        Control.initializaAll();

    }


    public static void main(String[] args) {
        launch(args);
    }

}