package com.larrainvial.logviwer;

import com.larrainvial.logviwer.event.ReadLogEvent;
import com.larrainvial.logviwer.utils.Control;
import com.larrainvial.trading.emp.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Collection;

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

        Repository.adrArbitrageXSGO_DOLAR_LOADER.setLocation(MainApp.class.getResource("view/ARDArbitrage_XSGO_MKD_DOLAR.fxml"));
        AnchorPane adrArbitrageXSGO_DOLAR_LOADER = (AnchorPane) Repository.adrArbitrageXSGO_DOLAR_LOADER.load();

        Repository.adrArbitrageXSGO_NYSE_LOADER.setLocation(MainApp.class.getResource("view/ARDArbitrage_XSGO_MKD_NYSE.fxml"));
        AnchorPane adrArbitrageXSGO_MKD_NYSE = (AnchorPane) Repository.adrArbitrageXSGO_NYSE_LOADER.load();


        AnchorPane AnchorPane = new AnchorPane();
        AnchorPane.getChildren().add(adrArbitrageXSGO_MKD_NYSE);
        AnchorPane.getChildren().add(adrArbitrageXSGO_DOLAR_LOADER);

        Repository.tabPanePrincipalTabPanel.getTabs().get(0).setContent(AnchorPane);


        rootLayout_Loader.setCenter(Repository.tabPanePrincipalTabPanel);


        Control.initialize();
        Controller.dispatchEvent(new ReadLogEvent(this, Repository.NameAdrArbitrageXSGO, Repository.DOLAR, Repository.fileDolar));

        Scene scene = new Scene(rootLayout_Loader);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {

        launch(args);
    }

}
