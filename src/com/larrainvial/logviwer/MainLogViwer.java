package com.larrainvial.logviwer;

import com.larrainvial.logviwer.utils.Control;
import com.larrainvial.logviwer.utils.Helper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;

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

            Scene scene = new Scene(rootLayout_Loader);
            primaryStage.setScene(scene);

            primaryStage.show();

            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension tamano = tk.getScreenSize();
            System.out.println("La pantalla es de " + tamano.getWidth() + " x " + tamano.getHeight());

            Control.initialize();
            Control.initializaAll();


        } catch (Exception e){
            Helper.exception(e);
        }

    }


}