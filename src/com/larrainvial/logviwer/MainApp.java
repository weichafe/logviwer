package com.larrainvial.logviwer;

import com.larrainvial.logviwer.event.ReadLogEvent;
import com.larrainvial.logviwer.utils.Control;
import com.larrainvial.trading.emp.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;


    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Log Viwer");

        this.initRootLayout();
        this.adrArbitrageXSGO();
        this.principalPanel();

        Control.initialize();
        Controller.dispatchEvent(new ReadLogEvent(this, Repository.NameAdrArbitrageXSGO, Repository.DOLAR, Repository.fileDolar));

    }


    public void initRootLayout() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/rootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void principalPanel() {

        try {

            Repository.principalPanel_LOADER.setLocation(MainApp.class.getResource("view/PrincipalTabPanel.fxml"));
            AnchorPane principalPanel = (AnchorPane) Repository.principalPanel_LOADER.load();
            rootLayout.setCenter(principalPanel);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adrArbitrageXSGO() {

        try {

            Repository.adrArbitrageXSGO_DOLAR_LOADER.setLocation(MainApp.class.getResource("view/ARDArbitrage_XSGO_DOLAR.fxml"));
            AnchorPane adrArbitrageXSGO_DOLAR_LOADER = (AnchorPane) Repository.adrArbitrageXSGO_DOLAR_LOADER.load();
            rootLayout.setCenter(adrArbitrageXSGO_DOLAR_LOADER);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        launch(args);
    }
}
