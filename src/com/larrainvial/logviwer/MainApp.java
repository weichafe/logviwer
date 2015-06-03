package com.larrainvial.logviwer;

import com.larrainvial.logviwer.utils.Control;
import com.larrainvial.logviwer.utils.Helper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {

            Repository.primaryStage = primaryStage;
            Repository.primaryStage.setTitle("Log Viwer");

            Repository.rootLayout_Loader.setLocation(MainApp.class.getResource("view/rootLayout.fxml"));
            BorderPane rootLayout_Loader = (BorderPane) Repository.rootLayout_Loader.load();

            Repository.principalTabPanel_Loader.setLocation(MainApp.class.getResource("view/PrincipalTabPanel.fxml"));
            Repository.tabPanePrincipalTabPanel = (TabPane) Repository.principalTabPanel_Loader.load();
            rootLayout_Loader.setCenter(Repository.tabPanePrincipalTabPanel);

            Scene scene = new Scene(rootLayout_Loader);
            primaryStage.setScene(scene);

            //Image image = new Image("batman.png");  //pass in the image path
            //scene.setCursor(new ImageCursor(image));

            primaryStage.show();

            Control.initialize();
            Control.initializaAll();


            //com.larrainvial.sellside.MainApp.sellside();

        } catch (Exception e){
            Helper.exception(e);
        }

    }


    public static void main(String[] args) {
        launch(args);
    }

}