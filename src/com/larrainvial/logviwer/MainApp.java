package com.larrainvial.logviwer;

import com.larrainvial.logviwer.utils.Helper;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {

            /*
            System.out.print("Inicializando servidor... ");

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

            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension tamano = tk.getScreenSize();
            System.out.println("La pantalla es de " + tamano.getWidth() + " x " + tamano.getHeight());

            Control.initialize();
            Control.initializaAll();

                 */
            com.larrainvial.sellside.MainApp.sellside();



        } catch (Exception e){
            Helper.exception(e);
        }

    }


    public static void main(String[] args) {

        launch(args);
    }

}