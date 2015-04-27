package com.larrainvial.logviwer;


import com.larrainvial.logviwer.event.ReadLogEvent;
import com.larrainvial.logviwer.utils.Control;
import com.larrainvial.trading.emp.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
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


        rootLayout_Loader.setCenter(Repository.tabPanePrincipalTabPanel);

        Scene scene = new Scene(rootLayout_Loader);
        primaryStage.setScene(scene);
        primaryStage.show();


        Control.initializaAll();
        Control.initialize();

        Controller.dispatchEvent(new ReadLogEvent(this, Repository.strategy.get("ADRArbitrageXSGO").getNameAlgo(), Repository.strategy.get("ADRArbitrageXSGO").getMkd_dolar(), Repository.strategy.get("ADRArbitrageXSGO").getMkd_dolar_file()));
        Thread.sleep(100);
        Controller.dispatchEvent(new ReadLogEvent(this, Repository.strategy.get("ADRArbitrageXSGO").getNameAlgo(), Repository.strategy.get("ADRArbitrageXSGO").getMkd_adr(), Repository.strategy.get("ADRArbitrageXSGO").getMkd_adr_file()));
        Thread.sleep(100);
        Controller.dispatchEvent(new ReadLogEvent(this, Repository.strategy.get("ADRArbitrageXSGO").getNameAlgo(), Repository.strategy.get("ADRArbitrageXSGO").getMkd_local(), Repository.strategy.get("ADRArbitrageXSGO").getMkd_local_file()));
        Thread.sleep(100);
        Controller.dispatchEvent(new ReadLogEvent(this, Repository.strategy.get("ADRArbitrageXSGO").getNameAlgo(), Repository.strategy.get("ADRArbitrageXSGO").getRouting_adr(), Repository.strategy.get("ADRArbitrageXSGO").getRouting_adr_file()));
        Thread.sleep(100);
        Controller.dispatchEvent(new ReadLogEvent(this, Repository.strategy.get("ADRArbitrageXSGO").getNameAlgo(), Repository.strategy.get("ADRArbitrageXSGO").getRouting_local(), Repository.strategy.get("ADRArbitrageXSGO").getRouting_local_file()));

    }


    public static void main(String[] args) {
        launch(args);
    }

}
