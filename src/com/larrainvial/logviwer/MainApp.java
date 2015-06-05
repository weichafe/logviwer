package com.larrainvial.logviwer;

import com.larrainvial.logviwer.server.ServidorHilo;
import com.larrainvial.logviwer.utils.Helper;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {

            ServerSocket ss;
            System.out.print("Inicializando servidor... ");

            try {

                ss = new ServerSocket(10578);

                System.out.println("\t[OK]");
                int idSession = 0;

                while (true) {

                    Socket socket;
                    socket = ss.accept();
                    System.out.println("Nueva conexión entrante: "+socket);
                    ((ServidorHilo) new ServidorHilo(socket, idSession)).start();
                    idSession++;
                }

            } catch (IOException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            }








            /*
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

            */

        } catch (Exception e){
            Helper.exception(e);
        }

    }


    public static void main(String[] args) {
        launch(args);
    }

}