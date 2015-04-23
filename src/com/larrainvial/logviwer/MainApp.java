package com.larrainvial.logviwer;

import com.larrainvial.logviwer.event.ReadLogEvent;
import com.larrainvial.logviwer.utils.Control;
import com.larrainvial.trading.emp.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Log Viwer");

        Repository.rootLayout_Loader.setLocation(MainApp.class.getResource("view/rootLayout.fxml"));
        BorderPane rootLayout_Loader = (BorderPane) Repository.rootLayout_Loader.load();

        Repository.principalTabPanel_Loader.setLocation(MainApp.class.getResource("view/PrincipalTabPanel.fxml"));
        Repository.tabPanePrincipalTabPanel = (TabPane) Repository.principalTabPanel_Loader.load();

        Repository.initializaAll();

        rootLayout_Loader.setCenter(Repository.tabPanePrincipalTabPanel);

        Scene scene = new Scene(rootLayout_Loader);
        primaryStage.setScene(scene);
        primaryStage.show();

        Control.initialize();

        Controller.dispatchEvent(new ReadLogEvent(this, Repository.NameAdrArbitrageXSGO, Repository.MKD_NYSE, Repository.file_adrArbitrageXsgo_mkd_nyse));
        Controller.dispatchEvent(new ReadLogEvent(this, Repository.NameAdrArbitrageXSGO, Repository.DOLAR, Repository.file_adrArbitrageXsgo_mkd_dolar));

    }


    public static void main(String[] args) {
        launch(args);
    }

}
